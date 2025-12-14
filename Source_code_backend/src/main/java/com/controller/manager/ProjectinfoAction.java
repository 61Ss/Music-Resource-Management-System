package com.controller.manager;

import com.entity.Projectinfo;
import com.entity.Projectinfo_type;
import com.service.*;
// import com.util.MyWordSegmenter;


import com.util.ResultUtil;
import com.interceptor.NoLoginInterface;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller

@RequestMapping("/manager/projectinfo")
public class ProjectinfoAction extends BaseManagerAction {

    @Resource
    private ProjectinfoService projectinfoService;
    @Resource
    private Projectinfo_typeService projectinfo_typeService;


    @RequestMapping("status.action")

    @ResponseBody
    public ResultUtil status(String id, int status, Map<String, Object> map) {

        Projectinfo projectinfo = projectinfoService.getById(id);
        projectinfo.setStatus(status);
        projectinfoService.update(projectinfo);
        return ResultUtil.ok();//
    }

    @RequestMapping("iscooled.action")

    @ResponseBody
    public ResultUtil iscooled(String id, int iscooled, Map<String, Object> map) {

        Projectinfo projectinfo = projectinfoService.getById(id);
        projectinfo.setIscooled(iscooled);
        projectinfoService.update(projectinfo);
        return ResultUtil.ok();//
    }

    @RequestMapping("projectinfo_flv.action")
    @ResponseBody
    public ResultUtil projectinfo_flv(String id, String projectinfo_flv, Map<String, Object> map) {

        Projectinfo projectinfo = projectinfoService.getById(id);
        projectinfo.setProjectinfo_isflv(projectinfo_flv);
        projectinfoService.update(projectinfo);
        return ResultUtil.ok();//
    }


    @RequestMapping("projectinfo_top.action")
    @ResponseBody
    public ResultUtil projectinfo_top(String id, String projectinfo_top, Map<String, Object> map) {

        Projectinfo projectinfo = projectinfoService.getById(id);
        projectinfo.setProjectinfo_istop(projectinfo_top);
        projectinfoService.update(projectinfo);
        return ResultUtil.ok();//
    }


    @RequestMapping("create.action")

    @ResponseBody
    public ResultUtil create(Map<String, Object> map) {
        Map<String, Object> parameter = new HashMap<String, Object>();

        List<Projectinfo_type> projectinfo_typeList = projectinfo_typeService.getAll(parameter);
        map.put("projectinfo_typeList", projectinfo_typeList);
        return ResultUtil.ok().data(map);//
    }


    @RequestMapping("add.action")

    @ResponseBody
    public ResultUtil add(Projectinfo projectinfo) {
        projectinfo.setIscooled(0);
        projectinfo.setStatus(0);
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date = formatter.format(currentTime);
        projectinfo.setCreatedate(date);
        projectinfoService.insert(projectinfo);
        return ResultUtil.ok();
    }

    @RequestMapping("delete.action")

    @ResponseBody
    public ResultUtil delete(String id) {
        projectinfoService.delete(id);
        return ResultUtil.ok();
    }


    @RequestMapping("update.action")

    @ResponseBody
    public ResultUtil update(Projectinfo projectinfo) {
        projectinfoService.update(projectinfo);
        return ResultUtil.ok();
    }


    @RequestMapping("getAll.action")

    @ResponseBody
    public ResultUtil getAll(HttpServletRequest request, Map<String, Object> map) throws UnsupportedEncodingException {
        Map<String, Object> parameter = new HashMap<String, Object>();
        String keyword = request.getParameter("keyword");
        if (keyword != null && keyword != "") {

            parameter.put("keyword", keyword);//
        }
        String projectinfo_typeid = request.getParameter("projectinfo_typeid");//
        if (projectinfo_typeid != null && projectinfo_typeid != "") {
            parameter.put("projectinfo_typeid", projectinfo_typeid);//
        }
        int rowCountTotal = projectinfoService.getRowSize(parameter);//
        int pageSize = 12;//
        int pageNumber = 1; //

        if (request.getParameter("pageNumber") != null && request.getParameter("pageNumber") != "") {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));//
        }
        parameter.put("limit", pageSize);//
        parameter.put("start", (pageNumber - 1) * pageSize);//
        List<Projectinfo> projectinfoList = projectinfoService.getAll(parameter);

        Map<String, Object> pager = new HashMap<String, Object>();
        int pageCount = 1;//
        //
        if (rowCountTotal % pageSize == 0) {
            pageCount = rowCountTotal / pageSize;
        } else {
            pageCount = rowCountTotal / pageSize + 1;
        }
        pager.put("pageNumber", pageNumber);//
        pager.put("pageCount", pageCount);//
        pager.put("rowCountTotal", rowCountTotal);//

        map.put("keyword", keyword);
        map.put("pager", pager);


        map.put("projectinfoList", projectinfoList);
        return ResultUtil.ok().data(map);//
    }

    @RequestMapping("getById.action")

    @ResponseBody
    public ResultUtil getById(String id, Map<String, Object> map) {
        Map<String, Object> parameter = new HashMap<String, Object>();
        Projectinfo projectinfo = projectinfoService.getById(id);
        map.put("projectinfo", projectinfo);

        List<Projectinfo_type> projectinfo_typeList = projectinfo_typeService.getAll(parameter);

        map.put("projectinfo_typeList", projectinfo_typeList);


        return ResultUtil.ok().data(map);//
    }




}
