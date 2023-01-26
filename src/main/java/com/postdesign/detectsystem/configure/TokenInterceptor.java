package com.postdesign.detectsystem.configure;

import com.alibaba.fastjson.JSONObject;
import com.postdesign.detectsystem.utils.TokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (token != null){
            boolean verify = TokenUtil.verify(token);
            if (verify){
                return true;
            }
        }
        try{
            JSONObject json = new JSONObject();
            json.put("msg","token verify fail");
            json.put("status","401");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out;
            out = response.getWriter();
            out.append(json.toString());
            return false;
        }catch (Exception e){
            e.printStackTrace();
            JSONObject json = new JSONObject();
            json.put("msg","error");
            json.put("status","500");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out;
            out = response.getWriter();
            out.append(json.toString());
            return false;
        }
    }
}

