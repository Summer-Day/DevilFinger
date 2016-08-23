package com.example.administrator.devilfinger.model;

import com.example.administrator.wangshuobaselib.lib.safe.JavaTypesHelper;

import org.json.JSONObject;

public class Error implements BaseError {

    public static final Error NETWORK_ERROR = new Error();
    public static final Error DATA_PARSED_ERROR = new Error();

    static {
        NETWORK_ERROR.returnCode = ErrorCode.NETWORK_ERROR + "";
        NETWORK_ERROR.returnMessage = "network failed.";
        NETWORK_ERROR.returnUserMessage = "连接网络失败";

        DATA_PARSED_ERROR.returnCode = ErrorCode.DATA_PARSED_ERROR + "";
        DATA_PARSED_ERROR.returnMessage = "data parse failed.";
        DATA_PARSED_ERROR.returnUserMessage = "数据解析错误，请重试。";
    }

    private String returnCode;
    private String returnMessage;
    private String returnUserMessage;

    public void parseJson(JSONObject json) {
        if (json == null) {
            return;
        }

        this.returnCode = json.optString("returnCode");
        this.returnMessage = json.optString("returnMessage");
        this.returnUserMessage = json.optString("returnUserMessage");
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public String getReturnUserMessage() {
        return returnUserMessage;
    }

    public void setReturnUserMessage(String returnUserMessage) {
        this.returnUserMessage = returnUserMessage;
    }

    public int getReturnCodeInt() {
        return JavaTypesHelper.toInt(returnCode, -1);
    }

}
