package com.ac.aclogin.mapper;

import com.ac.aclogin.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: zhanzheng.pang
 * @time: 2021/11/9
 */
@Mapper
public interface UserMapper {
    User selectOne(String userName);

    int insert(String userName,String passWord);
}
