package com.ac.aclogin.utils;

import lombok.Data;

/**
 * 返回结果
 * @author: zhanzheng.pang
 * @time: 2021/11/15
 */
@Data
public class Result {

    /** 返回代码*/
    private String code;

    /** 返回信息*/
    private String msg;

    /** 返回数据*/
    private Object data;

}
