package com.ac.aclogin.utils;

import com.ac.aclogin.pojo.User;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.netty.util.internal.StringUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhanzheng.pang
 * @time: 2021/11/16
 */
public class JwtUtil {

    /** 自定义的密钥 */
    private static final String jwtToken = "123456#@$$";

    /** 有效期  一天  毫秒 */
    private static final int time = 1000 * 60 * 60 * 24;

    /**生成一个token
        * @Param: user
        * @return: java.lang.String
        * @Author: zhanzheng.pang
        * @Date: 2021/11/17 11:09
    */
    public static String createToken(User user){
//        B部分
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId",user.getUserId());
        claims.put("userName",user.getUserName());
        claims.put("passWord",user.getPassWord());

        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,jwtToken)     //A部分
                .setClaims(claims)                //B部分
                .setIssuedAt(new Date())        //设置签发日期，保证token不重复
                .setExpiration(new Date(System.currentTimeMillis() + time));   //设置token有效期
        String token = jwtBuilder.compact();       //生成token
        return token;
    }

    /**解析接收到的token是否合法
        * @Param: token
        * @return: java.util.Map<java.lang.String,java.lang.Object>
        * @Author: zhanzheng.pang
        * @Date: 2021/11/17 11:09
    */
    public static Map<String,Object> cheakToken(String token){
        try {
            Jwt parse = Jwts.parser().setSigningKey(jwtToken).parse(token);
            return (Map<String, Object>) parse.getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
