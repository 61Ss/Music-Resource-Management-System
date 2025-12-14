package com.controller.front;

import com.entity.JwtEntityFront;
import com.entity.Members;
import com.service.MembersService;
import com.util.JwtUtil;
import com.util.MD5;

import com.util.ResultUtil;
import com.interceptor.NoLoginInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller

@RequestMapping("/front")
public class LoginFrontAction extends BaseFrontAction {


    @Resource
    private MembersService membersService;



    @Autowired
    private Environment environment;


    @RequestMapping("preLogin.action")
    @NoLoginInterface  
    @ResponseBody
    public ResultUtil prelogin() {

        return ResultUtil.ok();

    }


    @RequestMapping("login.action")
    @NoLoginInterface  
    @ResponseBody
    public ResultUtil login(Map<String, Object> map) {

        String username = this.getRequest().getParameter("username");
        String password = this.getRequest().getParameter("password");
        password = MD5.MD5(password);



        boolean is_login_code = Boolean.parseBoolean(environment.getProperty("is-login-code"));
        if (is_login_code) {


            String code = this.getRequest().getParameter("loginCode");
            String loginCode_create = this.getRequest().getParameter("loginCode_create");


            if (this.getRequest().getParameter("loginCode_create") == null) {
                return ResultUtil.error().message("Verification code expired!");

            }
            if (this.getRequest().getParameter("loginCode") == null) {
                return ResultUtil.error().message("Please enter verification code!");

            }
            if (!code.toUpperCase().equals(loginCode_create.toString().toUpperCase())) {

                return ResultUtil.error().message("Verification code error!");

            }
        }
        JwtEntityFront jwtEntityFront = new JwtEntityFront();

        Members u = new Members();
        u.setUsername(username);
        List<Members> membersList = membersService.getByCond(u);
        if (membersList.size() == 0) {

            return ResultUtil.error().message("Username does not exist!");


        } else {
            Members members = membersList.get(0);
            if (password.equals(members.getPassword())) {
                if ("1".equals(members.getIscooled())) {

                    return ResultUtil.error().message("Account is frozen and cannot login!");


                } else {

                    jwtEntityFront.setUserid(members.getMembersid());
                    jwtEntityFront.setMembers(members);
                    map.put("userid", members.getMembersid());

                    map.put("info", members);

                    this.getSession().setAttribute("userid", members.getMembersid());
                    this.getSession().setAttribute("members", members);
                    this.getSession().setAttribute("role", 1);
                    jwtEntityFront.setTime(new Date().getTime());
       
                    String jwt = JwtUtil.getJwtToken(jwtEntityFront, JwtUtil.DEFAULT_EXPIRE);

                    map.put("token", jwt);


                    return ResultUtil.ok().message("Login successful").data(map);


                }
            } else {
                return ResultUtil.error().message("Password error!");


            }
        }
    }


    @RequestMapping("exit.action")
    @NoLoginInterface  
    @ResponseBody
    public ResultUtil exit() {


        String jwt = JwtUtil.getJwtToken(null, 1000L);
        return ResultUtil.ok().message("Logout successful").data("token", jwt);
    }


}