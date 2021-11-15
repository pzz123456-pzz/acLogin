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

    @NotBlank
    private String userName;

    @NotBlank
    private String passWord;
}
