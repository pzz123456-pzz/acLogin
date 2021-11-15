package com.ac.aclogin.utils;

/**
 * @author: zhanzheng.pang
 * @time: 2021/11/15
 */
public class ResultUtil {

    public static Result success(Object data){
        Result result = new Result();
        result.setCode("1");
        result.setData(data);
        return result;
    }

    public static Result fail(String msg){
        Result result = new Result();
        result.setCode("2");
        result.setMsg(msg);
        return result;
    }
}
