package com.controller;

import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.manager.BaseManagerAction;

import com.util.ResultUtil;
import com.interceptor.NoLoginInterface;

import com.util.CpachaUtil;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


@Controller
@RequestMapping("/system")
public class SystemAction {

    @RequestMapping("main.action")
    @NoLoginInterface   
    @ResponseBody
    public ResultUtil main(Map<String, Object> map) {
        return ResultUtil.ok().data(map);
    }

    @RequestMapping(value = "/upload_action.action")
    @NoLoginInterface   
    @ResponseBody
    public ResultUtil upload_action(@RequestParam(value = "image", required = false) MultipartFile file, HttpServletRequest request, ModelMap model) {

        String path = System.getProperty("user.dir") + File.separator + "upload_files";
        String fileName = file.getOriginalFilename(); 
        int i = fileName.lastIndexOf(".");


        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MMddHHmmss");
        String name = formatter.format(currentTime);

        String type = fileName.substring(i + 1);

        int fourDigitRandomNum = (int) (Math.random() * 9000) + 1000;
        fileName = name + fourDigitRandomNum + "." + type;
        File targetFile = new File(path, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }


        try {
            file.transferTo(targetFile);
            return ResultUtil.ok().data("imageFileName", "upload_files/" + fileName);
        } catch (Exception e) {
            e.printStackTrace();


            return ResultUtil.error().message("fail to upload");
        }

    }


    @Autowired
    private Environment environment;

    /**
     * 
     *
     * @param vcodeLen
     * @param width
     * @param height
     * @param cpachaType:用来区别验证码的类型，传入字符串
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getCpacha.action")
    @NoLoginInterface    
    @ResponseBody

    public ResultUtil generateCpacha(
            @RequestParam(name = "vl", required = false, defaultValue = "4") Integer vcodeLen,
            @RequestParam(name = "w", required = false, defaultValue = "100") Integer width,
            @RequestParam(name = "h", required = false, defaultValue = "30") Integer height,
            @RequestParam(name = "type", required = true, defaultValue = "loginCpacha") String cpachaType,
            HttpServletRequest request,
            HttpServletResponse response, Map<String, Object> map) {
        CpachaUtil cpachaUtil = new CpachaUtil(vcodeLen, width, height);
        String generatorVCode = cpachaUtil.generatorVCode();
        request.getSession().setAttribute(cpachaType, generatorVCode);
        BufferedImage generatorRotateVCodeImage = cpachaUtil.generatorRotateVCodeImage(generatorVCode, true);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            ImageIO.write(generatorRotateVCodeImage, "gif", stream);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        byte[] bytes = Base64.encodeBase64(stream.toByteArray());
        String base64 = new String(bytes);


        Boolean auto_fill_login_username = Boolean.valueOf(environment.getProperty("auto-fill-login-username"));
        Boolean is_login_code = Boolean.valueOf(environment.getProperty("is-login-code"));


        map.put("is_login_code", is_login_code);
        map.put("auto_fill_login_username", auto_fill_login_username);


        map.put("base64", "data:image/jpeg;base64," + base64);
        map.put("login_code", generatorVCode);
        return ResultUtil.ok().data(map);

    }


}
