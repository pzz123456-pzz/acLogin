package com.ac.aclogin.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author: zhanzheng.pang
 * @time: 2021/11/15
 */
@Data
public class UserDto {

    private int userId;

    @NotBlank(message = "用户名不能为空")
    private String userName;

    @NotBlank(message = "密码不能为空")
    private String passWord;
}
