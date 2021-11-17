package com.ac.aclogin.filter;


import com.ac.aclogin.utils.JwtUtil;
import com.ac.aclogin.utils.MyError;
import com.ac.aclogin.utils.ResultUtil;
import org.junit.platform.commons.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @author: zhanzheng.pang
 * @time: 2021/11/17
 */
@WebFilter(urlPatterns = "/new/getToken")
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String token = httpServletRequest.getHeader("Authorization");

        /***
         *  以下代码注释掉  直接用
         *    filterChain.doFilter(servletRequest,servletResponse);
         *   的话是在impl中的   findUserByToken() 方法进行校验
         */

        if (StringUtils.isBlank(token)){
            System.out.println(" filter  ：" + MyError.MY_ERROR_3.getMsg());
        }else {
            Map<String, Object> map = JwtUtil.cheakToken(token);
            if (map == null){
                System.out.println("  filter  ： " + MyError.MY_ERROR_4.getMsg());
            }else {
//              reids中有效期到达，redis已经没有了token，但是生成的jwt有效期还没过，所以会执行以下代码
                filterChain.doFilter(servletRequest,servletResponse);
            }
        }


    }

    @Override
    public void destroy() {

    }
}

