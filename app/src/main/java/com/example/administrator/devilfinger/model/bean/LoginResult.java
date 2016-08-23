package com.example.administrator.devilfinger.model.bean;

import com.example.administrator.devilfinger.model.JDBBaseResult;

import java.io.Serializable;

/**
 * com.example.administrator.devilfinger.model.bean.LoginResult
 *
 * @author 王硕
 * @date 2016/8/22 15:21
 * @desc 文件描述
 */
public class LoginResult  extends JDBBaseResult{
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data implements Serializable {


    }
}
