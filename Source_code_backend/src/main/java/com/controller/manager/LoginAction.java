package com.controller.manager;

import com.entity.Manager;
import com.entity.JwtEntityManager;
import com.service.ManagerService;
import com.util.JwtUtil;
import com.util.MD5;

import com.util.ResultUtil;
import com.interceptor.NoLoginInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller

@RequestMapping("/manager/login")
public class LoginAction extends BaseManagerAction {

    @Resource
    private ManagerService managerService;

    @Autowired
    private Environment environment;

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
        JwtEntityManager jwtEntityManager = new JwtEntityManager();

        Manager managerEntity = new Manager();
        managerEntity.setUsername(username);
        List<Manager> managerlist = managerService.getByCond(managerEntity);
        if (managerlist.size() == 0) {
            return ResultUtil.error().message("Username does not exist!");

        } else {
            Manager manager = managerlist.get(0);
            if (password.equals(manager.getPassword())) {

                jwtEntityManager.setManagerid(manager.getManagerid());
                jwtEntityManager.setUsername(manager.getUsername());
                jwtEntityManager.setRealname(manager.getRealname());

                map.put("managerid", manager.getManagerid());
                map.put("username", manager.getUsername());
                map.put("info", manager);


                this.getRequest().getSession().setAttribute("managerid", manager.getManagerid());
                this.getRequest().getSession().setAttribute("username", manager.getUsername());
                this.getRequest().getSession().setAttribute("realname", manager.getRealname());
                jwtEntityManager.setTime(new Date().getTime());
               
                String jwt = JwtUtil.getJwtToken(jwtEntityManager, JwtUtil.DEFAULT_EXPIRE);

                map.put("token", jwt);


                return ResultUtil.ok().message("Login successful").data(map);

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
