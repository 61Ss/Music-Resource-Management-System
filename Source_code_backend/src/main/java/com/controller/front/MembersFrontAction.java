package com.controller.front;

import com.entity.Members;
import com.service.MembersService;
import com.util.MD5;

import com.util.ResultUtil;
import com.interceptor.NoLoginInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.text.SimpleDateFormat;

import java.util.*;

@Controller

@RequestMapping("/front")
public class MembersFrontAction extends BaseFrontAction {


    @Resource
    private MembersService membersService;



    // 准备注册
    @RequestMapping("preReg.action")
    @NoLoginInterface   
    @ResponseBody
    public ResultUtil preReg() {

        return ResultUtil.ok();

    }


    @RequestMapping("register.action")
    @NoLoginInterface  
    @ResponseBody
    public ResultUtil register(Members members, Map<String, Object> map) {


        boolean name_true = true;
        boolean email_true = true;

        Members u = new Members();
        u.setUsername(members.getUsername());
        members.setPassword(MD5.MD5(members.getPassword()));
        List<Members> membersList = membersService.getByCond(u);
        if (membersList.size() == 0) {


        } else {
            name_true = false;

        }


        Members uu = new Members();
        uu.setEmail(members.getEmail());
        List<Members> membersList11 = membersService.getByCond(uu);
        if (membersList11.size() == 0) {


        } else {
            email_true = false;
        }


        if (!name_true) {
            return ResultUtil.error().message("Username already exists");
        } else if (!email_true) {
            return ResultUtil.error().message("Email already exists");
        }
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        members.setRegdate(dateString);

        membersService.insert(members);


        return ResultUtil.ok().message("Registration successful, please login");


    }


    @RequestMapping("prePwd.action")
    @NoLoginInterface  
    @ResponseBody
    public ResultUtil prePwd() {


        return ResultUtil.ok();//

    }


    @Autowired
    private JavaMailSender javaMailSender;


    @Value("${spring.mail.username}")
    private String mailusername;


    @RequestMapping("sendMail03.action")
    @NoLoginInterface   
    @ResponseBody
    public ResultUtil sendMail03(String email, Map<String, Object> map) {


        Members uu = new Members();
        uu.setEmail(email);
        List<Members> membersList11 = membersService.getByCond(uu);
        if (membersList11.size() == 0) {


            return ResultUtil.ok().message("Email not found in system");

        }

        int code = 0;

        try {

            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom(mailusername);
            message.setTo(email);
            message.setSubject("EchoVerse: Your Verification Code");
            code = (int) (Math.random() * 8998) + 1000 + 1;
            message.setText("Hello!\r\n"
            + "Your verification code for EchoVerse system password recovery is: \r\n" 
            + code + "\r\n"
            + "Please do not provide the verification code to others. If it is not your operation, please ignore it.");

            Members user1111 = membersList11.get(0);
            user1111.setPwdback_code(String.valueOf(code));
            membersService.update(user1111);

            System.out.println(code);
            javaMailSender.send(message);


        } catch (Exception e) {
            e.printStackTrace();
        }

        map.put("code", code);
        return ResultUtil.ok().data(map);
    }


    @RequestMapping("sendMail04.action")
    @NoLoginInterface  
    @ResponseBody
    public ResultUtil sendMail04(String email, Map<String, Object> map) {


        Members uu = new Members();
        uu.setEmail(email);
        List<Members> membersList11 = membersService.getByCond(uu);
        if (membersList11.size() > 0) {


            return ResultUtil.ok().message("Email already exists in system");

        }

        int code = 0;

        try {

            SimpleMailMessage message = new SimpleMailMessage();
           


            message.setFrom(mailusername);
            message.setTo(email);
            message.setSubject("EchoVerse: Your Verification Code");
            code = (int) (Math.random() * 8998) + 1000 + 1;
            message.setText("Hello!\r\n"
            + "Your verification code for EchoVerse system registration is: \r\n" 
            + code + "\r\n"
            + "Please do not provide the verification code to others. If it is not your operation, please ignore it.");


            System.out.println(code);
            javaMailSender.send(message);


        } catch (Exception e) {
            e.printStackTrace();
        }

        map.put("code", code);
        return ResultUtil.ok().data(map);
    }


    @RequestMapping("pwdpack.action")
    @NoLoginInterface  
    @ResponseBody
    public ResultUtil pwdpack(String email, String pwdback_code, Map<String, Object> map) {


        Map<String, Object> parameter = new HashMap<String, Object>();


        parameter.put("email", email);
        parameter.put("pwdback_code", pwdback_code);


        List<Members> membersList11 = membersService.getAll(parameter);
        if (membersList11.size() == 0) {


            return ResultUtil.ok().message("User not found or incorrect code");

        } else {

            Members user1111 = membersList11.get(0);


            int code = (int) (Math.random() * 899898) + 100000 + 1;
            String password = String.valueOf(code);
            String md5_password = MD5.MD5(password);
            user1111.setPassword(md5_password);

            System.out.println(code);

            membersService.update(user1111);

            map.put("password", password);
            return ResultUtil.ok().data(map);


        }


    }

    @RequestMapping("edit_password.action")
    @ResponseBody
    public ResultUtil edit_password(Map<String, Object> map) {

        String membersid = (String) this.getSession().getAttribute("userid");
        String password = this.getRequest().getParameter("password");
        password = MD5.MD5(password);
        String repassword = this.getRequest().getParameter("repassword");
        repassword = MD5.MD5(repassword);
        Members members = membersService.getById(membersid);
        if (password.equals(members.getPassword())) {
            members.setPassword(repassword);
            membersService.update(members);
            return ResultUtil.ok().message("Modified successfully ");

        } else {
            return ResultUtil.error().message("Old password error");

        }
    }


    @RequestMapping("preInfo.action")
    @NoLoginInterface  
    @ResponseBody
    public ResultUtil preInfo(String id, Map<String, Object> map) {

        Members members = membersService.getById(id);
        map.put("members", members);
        return ResultUtil.ok().data(map);

    }


    @RequestMapping("edit_info.action")

    @ResponseBody
    public ResultUtil edit_info(Members members, Map<String, Object> map) {


        membersService.update(members);
        return ResultUtil.ok().message("Modified successfully");


    }


}
