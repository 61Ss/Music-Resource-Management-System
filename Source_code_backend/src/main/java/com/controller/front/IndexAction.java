package com.controller.front;

import com.entity.*;
import com.service.*;

import com.util.ResultUtil;
import com.interceptor.NoLoginInterface;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller

@RequestMapping("/front")
public class IndexAction extends BaseFrontAction {


    @Resource
    private Projectinfo_typeService projectinfo_typeService;


    @Resource
    private ProjectinfoService projectinfoService;

    @RequestMapping("index.action")
    @NoLoginInterface   
    @ResponseBody
    public ResultUtil index(Map<String, Object> map) {
        return ResultUtil.ok().message("Backend server started successfully.");
    }


    //获取音乐分类
    @RequestMapping("projectinfo_typeList.action")
    @NoLoginInterface    
    @ResponseBody
    public ResultUtil projectinfo_typeList(Map<String, Object> map) {
        Map<String, Object> parameter = new HashMap<String, Object>();
        List<Projectinfo_type> projectinfo_typeList = projectinfo_typeService.getAll(parameter);

        map.put("projectinfo_typeList", projectinfo_typeList);


        return ResultUtil.ok().data(map);


    }



    @RequestMapping("front_default.action")
    @NoLoginInterface   
    @ResponseBody
    public ResultUtil front_default(Map<String, Object> map) {
        Map<String, Object> parameter = new HashMap<String, Object>();


        List<Projectinfo> favList_projectinfo = projectinfoService.getFlv();
        map.put("favList_projectinfo", favList_projectinfo);

        List<Projectinfo> topList_projectinfo = projectinfoService.getTop();
        map.put("topList_projectinfo", topList_projectinfo);


        Map<String, Object> parameter1122 = new HashMap<String, Object>();
        parameter1122.put("limit", 10);
        parameter1122.put("start", 0);
        parameter1122.put("iscooled", 0);
        parameter1122.put("order_by", "hits");
        parameter1122.put("status", 0);

        List<Projectinfo> hitsList_projectinfo = projectinfoService.getAll(parameter1122);
        map.put("hitsList_projectinfo", hitsList_projectinfo);


        Map<String, Object> parameter11 = new HashMap<String, Object>();
        parameter11.put("limit", 10);
        parameter11.put("start", 0);
        parameter11.put("iscooled", 0);
        parameter11.put("status", 0);


        List<Projectinfo> projectinfoList = projectinfoService.getAll(parameter11);

        map.put("projectinfoList", projectinfoList);

        List<Projectinfo_type> projectinfo_typeList = projectinfo_typeService.getAll(null);
        List<Projectinfo_type> frontList_projectinfo_type = new ArrayList<Projectinfo_type>();
        for (Projectinfo_type projectinfo_type : projectinfo_typeList) {
            List<Projectinfo> projectinfoList_1 = projectinfoService.getByProjectinfo_type(projectinfo_type.getProjectinfo_typeid());
            projectinfo_type.setProjectinfoList(projectinfoList_1);
            frontList_projectinfo_type.add(projectinfo_type);
        }
        map.put("frontList_projectinfo_type", frontList_projectinfo_type);


        return ResultUtil.ok().data(map);
    }


}
