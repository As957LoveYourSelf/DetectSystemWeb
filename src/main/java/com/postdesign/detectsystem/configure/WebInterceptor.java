package com.postdesign.detectsystem.configure;

import com.postdesign.detectsystem.service.currencyService.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;

public class WebInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(WebInterceptor.class);

    @Autowired
    RedisService service;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        String methodName = method.getName();
        logger.info("拦截到了方法"+methodName+",在该方法执行之前执行");
        // 返回true才会继续执行，返回false则取消当前请求
        // Token
        String token = request.getHeader("Authorization");
        if (token == null){
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out;
            out = response.getWriter();
            out.write("Authorization is null");
            return false;
        }
        Object o = service.get(token);
        if (o != null){
            logger.info("获取到Token: "+token);
            service.update(token);
            return true;
        }
        try{
            logger.info("Token验证失败");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out;
            out = response.getWriter();
            out.write("token verify fail");
            return false;
        }catch (Exception e){
            e.printStackTrace();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out;
            out = response.getWriter();
            out.write("error");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse
            response, Object handler, ModelAndView modelAndView) throws Exception {
//        logger.info("执行完方法之后进执行(Controller方法调用之后)，但是此时还没进行视图渲染");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse
            response, Object handler, Exception ex) throws Exception {
//        logger.info("整个请求都处理完，DispatcherServlet也渲染了对应的视图，此时可以做一些清理的工作");
    }
}
