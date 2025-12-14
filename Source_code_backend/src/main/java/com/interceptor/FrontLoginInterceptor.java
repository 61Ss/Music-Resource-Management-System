package com.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.entity.JwtEntityFront;
import com.entity.Members;
import com.service.MembersService;
import com.util.JwtUtil;

import com.util.ResultUtil;
import com.interceptor.NoLoginInterface;

import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * @author superman
 */
public class FrontLoginInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest arg0,
                                HttpServletResponse arg1, Object handler, Exception arg3)
            throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
                           Object handler, ModelAndView arg3) throws Exception {
        // TODO Auto-generated method stub

    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        // TODO Auto-generated method stub
        String requestURI = request.getRequestURI();


        try {
            
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            NoLoginInterface noLoginInterface = handlerMethod.getMethod().getAnnotation(NoLoginInterface.class);
            if (noLoginInterface != null) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("访问接口未定义");

            e.printStackTrace();
        }
        
            String token = request.getHeader("token");
            if (StringUtils.isBlank(token)) {
                token = request.getParameter("token");
            }
        if (JwtUtil.checkToken(token)) {

            token = refreshToken(token, response, request);

            return true;
        }
        
        returnJsonData(response);
        System.out.println("front链接" + requestURI + "进入拦截器！");
        return false;
    }


    public void returnJsonData(HttpServletResponse response) {
        PrintWriter pw = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            pw = response.getWriter();
            pw.print(com.alibaba.fastjson.JSONObject.toJSONString(ResultUtil.error().message("token 登录失效或过期").code(HttpStatus.SC_UNAUTHORIZED)));
        } catch (IOException e) {

            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    @Resource
    private MembersService membersService;

    /**
     *
     * @param token 旧 token 值
     * @return 新 token
     */
    public String refreshToken(String token, HttpServletResponse response, HttpServletRequest request) {

        Claims claims = JwtUtil.getTokenBody(token);
       
        Long expire = claims.getExpiration().getTime();

        Long currentTime = new Date().getTime();



        JwtEntityFront jwtEntityFront = new JwtEntityFront();

        Object map = claims.get("data");
        try {

            com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.toJSON(map);
            jwtEntityFront = (JwtEntityFront) JSONObject.toJavaObject(jsonObject, JwtEntityFront.class);


            HttpSession session = request.getSession(); 


            Members members = membersService.getById(jwtEntityFront.getUserid());

            session.setAttribute("userid", members.getMembersid());
            session.setAttribute("members", members);
                session.setAttribute("role", 1);


        } catch (Exception e) {
            e.printStackTrace();
        }

        if ((expire - currentTime) / 1000 < 300) {

            token = JwtUtil.getJwtToken(jwtEntityFront, JwtUtil.DEFAULT_EXPIRE);


            response.setHeader("token", token);
        }
        return token;
    }

}