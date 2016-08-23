package com.example.administrator.devilfinger.model;

import com.example.administrator.wangshuobaselib.lib.util.JDBLog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 错误码定义：201开头为支付相关，202开头为用户相关
 */
public class JDBBaseResult implements RRHBaseResult {
    /**
     * 本地定义错误码使用负数
     */

    public static final int JDB_REQUEST_SUCCESS = 0; // 请求成功

    public static final int JDB_REQUEST_ERROR = 1; // JDB请求失败

    protected Error error;


    public JDBBaseResult() {
    }

    @Override
    public Error getError() {
        return this.error;
    }

    public void setError(Error e) {
        this.error = e;
    }

//    public void setToNetworkError() {
//        this.error = Error.NETWORK_ERROR;
//        if (ApnManager.isNetworkAvailable()) {
//            try {
//                this.error.setReturnUserMessage(JDBBaseApplication.sharedInstance().getString(R.string.network_fail));
//            } catch (Resources.NotFoundException ex) {
//                this.error.setReturnUserMessage("连接网络失败");
//            }
//
//        } else {
//            try {
//                this.error.setReturnUserMessage(JDBBaseApplication.sharedInstance().getString(
//                        R.string.network_not_available));
//            } catch (Resources.NotFoundException ex) {
//                this.error.setReturnUserMessage("网络未连接，请检查您的网络");
//            }
//        }
//    }

    public void setToDataParsedError() {
        this.error = Error.DATA_PARSED_ERROR;
    }

    @Override
    public String getReturnUserMessage() {
        if (error != null) {
            return error.getReturnUserMessage();
        } else {
            return Error.DATA_PARSED_ERROR.getReturnUserMessage();
        }
    }

    public String getReturnMessage() {
        if (error != null) {
            return error.getReturnMessage();
        } else {
            return Error.DATA_PARSED_ERROR.getReturnMessage();
        }
    }

    @Override
    public int getReturnCode() {
        int ret = -1;
        if (error != null) {
            ret = error.getReturnCodeInt();
        }
        return ret;
    }

    public boolean isSuccessfulRequest() {
        return JDB_REQUEST_SUCCESS == getReturnCode();
    }

    public void parseJson(String json) {
        if (json == null) {
            return;
        }

        try {
            JSONObject obj = new JSONObject(json);

            parseJson(obj);
        } catch (JSONException e) {
            JDBLog.detailException(e);
        }
    }

    public void parseJson(JSONObject json) {
        if (json == null) {
            return;
        }

        this.error = new Error();
        this.error.parseJson(json.optJSONObject("error"));

        this.parseJsonData(json.optJSONObject("data"));
    }

    protected void parseJsonData(JSONObject json) {

    }

}

