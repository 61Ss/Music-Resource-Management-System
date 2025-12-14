package com.controller.manager;

import com.entity.Manager;
import com.service.ManagerService;
import com.util.MD5;

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

@RequestMapping("/manager/manager")
public class ManagerAction extends BaseManagerAction {

    @Resource
    private ManagerService managerService;



    @RequestMapping("edit_password.action")

@ResponseBody
    public ResultUtil edit_password(HttpServletRequest request) {
        String managerid = (String) request.getSession().getAttribute("managerid");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        String md5pw = MD5.MD5(password);//MD5
        Manager manager = managerService.getById(managerid);
        if (md5pw.equals(manager.getPassword())) {
            String md5pw_new = MD5.MD5(repassword);//
            manager.setPassword(md5pw_new);
            managerService.update(manager);
        } else {

            return ResultUtil.error().message("Incorrect old password!");

        }
        return ResultUtil.ok();//

    }


    @RequestMapping("create.action")

@ResponseBody
    public ResultUtil create(Map<String, Object> map) {

        return ResultUtil.ok().data(map);
    }


    @RequestMapping("add.action")

@ResponseBody
    public ResultUtil add(Manager manager) {
        manager.setPassword(MD5.MD5(manager.getPassword()));//MD5

        managerService.insert(manager);
        return ResultUtil.ok();
    }

    @RequestMapping("delete.action")

@ResponseBody
    public ResultUtil delete(String id) {
        managerService.delete(id);
        return ResultUtil.ok();
    }

    @RequestMapping("update.action")

@ResponseBody
    public ResultUtil update(Manager manager) {
        manager.setPassword(MD5.MD5(manager.getPassword()));

        managerService.update(manager);
        return ResultUtil.ok();
    }

    @RequestMapping("getAll.action")

@ResponseBody
    public ResultUtil getAll(HttpServletRequest request, Map<String, Object> map) throws UnsupportedEncodingException {
        Map<String, Object> parameter = new HashMap<String, Object>();
        String keyword = request.getParameter("keyword");
        if (keyword != null && keyword != "") parameter.put("keyword", keyword);

        int rowCountTotal = managerService.getRowSize(parameter);
        int pageSize = 12;
        int pageNumber = 1;

        if (request.getParameter("pageNumber") != null && request.getParameter("pageNumber") != "") {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        }
        parameter.put("limit", pageSize);
        parameter.put("start", (pageNumber - 1) * pageSize);
        List<Manager> managerList = managerService.getAll(parameter);


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


        map.put("managerList", managerList);
        map.put("keyword", keyword);
        map.put("pager", pager);
        return ResultUtil.ok().data(map);//
    }


    @RequestMapping("getById.action")

@ResponseBody
    public ResultUtil getById(String id, Map<String, Object> map) {
        Manager manager = managerService.getById(id);
        map.put("manager", manager);
        return ResultUtil.ok().data(map);
    }


}
