package com.controller.manager;

import com.entity.Projectinfo_type;
import com.service.Projectinfo_typeService;

import com.util.ResultUtil;
import com.interceptor.NoLoginInterface;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller

@RequestMapping("/manager/projectinfo_type")
public class Projectinfo_typeAction extends BaseManagerAction {

    @Resource
    private Projectinfo_typeService projectinfo_typeService;


    @RequestMapping("create.action")
    @NoLoginInterface    

    @ResponseBody
    public ResultUtil create(Map<String, Object> map) {
        return ResultUtil.ok().data(map);
    }


    @RequestMapping("add.action")
    @NoLoginInterface  

    @ResponseBody
    public ResultUtil add(Projectinfo_type projectinfo_type) {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("name", projectinfo_type.getProjectinfo_typename()); 
        List<Projectinfo_type> list = projectinfo_typeService.getAll(parameter);
        if (!list.isEmpty()) {
            return ResultUtil.error().message("Name " + projectinfo_type.getProjectinfo_typename() + " Already exists.");
        }
        projectinfo_typeService.insert(projectinfo_type);
        return ResultUtil.ok();
    }


    @RequestMapping("delete.action")
    @NoLoginInterface  

    @ResponseBody
    public ResultUtil delete(String id) {
        projectinfo_typeService.delete(id);
        return ResultUtil.ok();
    }

    @RequestMapping("update.action")
    @NoLoginInterface 

    @ResponseBody
    public ResultUtil update(Projectinfo_type projectinfo_type) {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("name", projectinfo_type.getProjectinfo_typename()); 
        List<Projectinfo_type> list = projectinfo_typeService.getAll(parameter);
        if (!list.isEmpty()) {
            return ResultUtil.error().message("Already exists. " + projectinfo_type.getProjectinfo_typename() + " Already exists");
        }
        projectinfo_typeService.update(projectinfo_type);
        return ResultUtil.ok();
    }

    @RequestMapping("getAll.action")
    @NoLoginInterface  

    @ResponseBody
    public ResultUtil getAll(HttpServletRequest request, Map<String, Object> map) throws UnsupportedEncodingException {
        Map<String, Object> parameter = new HashMap<String, Object>();
        String keyword = request.getParameter("keyword");//
        if (keyword != null && keyword != "") {
            parameter.put("keyword", keyword);
        }
        int rowCountTotal = projectinfo_typeService.getRowSize(parameter);
        int pageSize = 12;
        int pageNumber = 1;

        if (request.getParameter("pageNumber") != null && request.getParameter("pageNumber") != "") {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        }
        parameter.put("limit", pageSize);
        parameter.put("start", (pageNumber - 1) * pageSize);
        List<Projectinfo_type> projectinfo_typeList = projectinfo_typeService.getAll(parameter);

        Map<String, Object> pager = new HashMap<String, Object>();
        int pageCount = 1;

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

        map.put("projectinfo_typeList", projectinfo_typeList);
        return ResultUtil.ok().data(map);//
    }



    @RequestMapping("getById.action")
    @NoLoginInterface 

    @ResponseBody
    public ResultUtil getById(String id, Map<String, Object> map) {
        Projectinfo_type projectinfo_type = projectinfo_typeService.getById(id);
        map.put("projectinfo_type", projectinfo_type);
        return ResultUtil.ok().data(map);//
    }


}
