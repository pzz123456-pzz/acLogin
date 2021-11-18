package com.ac.aclogin.utils;

/**
 * @author: zhanzheng.pang
 * @time: 2021/11/17
 */
public enum MyError {
    /**
     * 错误
      */
    MY_ERROR_1("没有这个人"),
    MY_ERROR_2("用户名或密码错误"),
    MY_ERROR_3("token不存在"),
    MY_ERROR_4("token不合法"),
    MY_ERROR_5("redis没有，已经过期"),

    My_ERROR_6("用户名已存在"),
    My_ERROR_7("注册失败"),
    My_ERROR_8("传参有错误");


    private String msg;




    MyError(String msg) {
        this.msg = msg;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
