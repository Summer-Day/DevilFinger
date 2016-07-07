package com.example.administrator.devilfinger.lib.util.CommonNet;

import android.text.TextUtils;

import com.example.administrator.devilfinger.lib.safe.JavaTypesHelper;
import com.example.administrator.devilfinger.lib.util.JDBLog;
import com.example.administrator.devilfinger.lib.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Url工具类
 * Created by abu on 2015/12/28.
 */
public class UrlUtils {
    public static class HttpRequest {
        private Map<String, Object> params;

        private String url;

        public HttpRequest(String url, Map<String, Object> params) {
            this.params = params;
            this.url = url;
        }

        public String getActionUrl() {
            return url;
        }

        public void addParameter(String key, String value) {
            if (params == null) {
                params = new HashMap<String, Object>();
            }

            params.put(key, value);
        }

        public Set<String> getKeys() {
            if (params == null) {
                return null;
            }

            return params.keySet();
        }

        public int getParameterAsInt(String name, int defaultValue) {
            if (params == null) {
                return defaultValue;
            }

            Object obj = params.get(name);

            if (obj == null) {
                return defaultValue;
            }

            String result = null;

            if (obj instanceof String[]) {
                String[] ss = (String[]) obj;

                if (ss.length > 0) {
                    result = ss[0];
                } else {
                    result = null;
                }
            } else {
                result = obj.toString();
            }

            return JavaTypesHelper.toInt(result, defaultValue);
        }

        public String getParameter(String name) {
            if (params == null) {
                return null;
            }

            Object obj = params.get(name);

            if (obj == null) {
                return null;
            }

            if (obj instanceof String[]) {
                String[] ss = (String[]) obj;

                if (ss.length > 0) {
                    return ss[0];
                } else {
                    return null;
                }
            }

            return obj.toString();
        }

    }

    /**
     *
     * @param url
     * @return
     */
    public static HttpRequest parseURLWithUTF8Encoding(String url) {
        if (url == null) {
            return new HttpRequest(null, null);
        }

        int queryStringStart = url.indexOf('?');
        if (queryStringStart == -1 || queryStringStart >= url.length() - 1) {
            return new HttpRequest(url, null);
        }

        String head = url.substring(0, queryStringStart);
        String queryString = url.substring(queryStringStart + 1);

        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            parseParameters(map, queryString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            JDBLog.detailException(e);
        }

        return new HttpRequest(head, map);
    }

    /**
     *
     * @param map
     * @param data
     * @param encoding
     * @throws UnsupportedEncodingException
     */
    protected static void parseParameters(Map<String, Object> map, String data, String encoding)
            throws UnsupportedEncodingException {
        if ((data == null) || (data.length() <= 0)) {
            return;
        }

        byte[] bytes = null;
        try {
            if (encoding == null)
                bytes = data.getBytes();
            else
                bytes = data.getBytes(encoding);
        } catch (UnsupportedEncodingException uee) {
        }
        parseParameters(map, bytes, encoding);
    }

    /**
     *解析
     * @param map
     * @param data
     * @param encoding
     * @throws UnsupportedEncodingException
     */
    protected static void parseParameters(Map<String, Object> map, byte[] data, String encoding)
            throws UnsupportedEncodingException {
        if ((data != null) && (data.length > 0)) {
            int ix = 0;
            int ox = 0;
            String key = null;
            String value = null;
            while (ix < data.length) {
                byte c = data[(ix++)];
                switch ((char) c) {
                    case '&':
                        value = new String(data, 0, ox, encoding);
                        if (key != null) {
                            putMapEntry(map, key, value);
                            key = null;
                        }
                        ox = 0;
                        break;
                    case '=':
                        if (key == null) {
                            key = new String(data, 0, ox, encoding);
                            ox = 0;
                        } else {
                            data[(ox++)] = c;
                        }
                        break;
                    case '+':
                        data[(ox++)] = 32;
                        break;
                    case '%':
                        if (ix >= data.length) {
                            data[(ox++)] = '%';
                        } else if (ix == data.length - 1) {
                            data[(ox++)] = '%';
                            data[(ox++)] = data[(ix++)];
                        } else {
                            data[(ox++)] = (byte) ((convertHexDigit(data[(ix++)]) << 4) + convertHexDigit(data[(ix++)]));
                        }

                        break;
                    default:
                        data[(ox++)] = c;
                }
            }

            if (key != null) {
                value = new String(data, 0, ox, encoding);
                putMapEntry(map, key, value);
            }
        }
    }

    /**
     * 把name和value放入map中
     * @param map
     * @param name
     * @param value
     */
    private static void putMapEntry(Map<String, Object> map, String name, String value) {
        String[] newValues = null;
        String[] oldValues = (String[]) (String[]) map.get(name);
        if (oldValues == null) {
            newValues = new String[1];
            newValues[0] = value;
        } else {
            newValues = new String[oldValues.length + 1];
            System.arraycopy(oldValues, 0, newValues, 0, oldValues.length);
            newValues[oldValues.length] = value;
        }
        map.put(name, newValues);
    }

    /**
     * 十六进制转换
     * @param b
     * @return
     */
    private static byte convertHexDigit(byte b) {
        if ((b >= 48) && (b <= 57))
            return (byte) (b - 48);
        if ((b >= 97) && (b <= 102))
            return (byte) (b - 97 + 10);
        if ((b >= 65) && (b <= 70))
            return (byte) (b - 65 + 10);
        return 0;
    }

    /**
     * 获取URLencode编码
     *
     * @param s URL字符串
     * @return String
     * 输入的url对应的encode编码
     */
    public static String getUrlEncode(String s) {
        if (s == null) {
            return null;
        }
        String result = "";
        try {
            result = URLEncoder.encode(s, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 解析Url 中key value：例如 key1=value1&key2= value2 如果解析内容较多需要异步操作
     *
     * @param src 待解析的字符串
     * @return Map
     * <key,value>形式的键值对
     */
    public static Map<String, String> paserUrlKey(String src) {
        Map<String, String> params = new HashMap<String, String>();

        if (StringUtils.isEmpty(src)) {
            return params;
        }
        String[] array = src.split("&");

        if (null == array || 0 == array.length) {
            return params;
        }

        for (String value : array) {
            String[] val = value.split("=");
            if (null == val || val.length < 2) {
                continue;
            }
            if (StringUtils.isEmpty(val[0]) || StringUtils.isEmpty(val[1])) {
                continue;
            }
            params.put(val[0], val[1]);
        }
        return params;
    }

    /**
     * 拼接url地址
     *
     * @param params
     * @return
     */
    public static String encodeUrl(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        if (params == null) {
            return url;
        }

        if (false == TextUtils.isEmpty(url)) {
            sb.append(url);
        }

        boolean first = true;
        for (String key : params.keySet()) {
            String pa = params.get(key);
            if (!TextUtils.isEmpty(pa)) {
                if (first) {
                    sb.append("?");
                    first = false;
                } else {
                    sb.append("&");
                }
                sb.append(key + "=" + URLEncoder.encode(pa));
            }
        }
        return sb.toString();
    }
}
