package com.example.administrator.devilfinger.model;


public interface RRHBaseResult {


    public int getReturnCode();

    public String getReturnUserMessage();

    public BaseError getError();
}