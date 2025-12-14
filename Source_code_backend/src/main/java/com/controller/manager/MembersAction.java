package com.controller.manager;

import com.entity.Members;
import com.service.MembersService;

import com.util.ResultUtil;
import com.interceptor.NoLoginInterface;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller

@RequestMapping("/manager/members")
public class MembersAction extends BaseManagerAction {

    @Resource
    private MembersService membersService;

    @RequestMapping("delete.action")

    @ResponseBody
    public ResultUtil delete(String id) {
        membersService.delete(id);
        return ResultUtil.ok();
    }

    @RequestMapping("iscooled.action")

    @ResponseBody
    public ResultUtil iscooled(String id, String iscooled) {
        Members members = membersService.getById(id);
        members.setIscooled(iscooled);
        membersService.update(members);
        return ResultUtil.ok();
    }

    @RequestMapping("getAll.action")

    @ResponseBody
    public ResultUtil getAll(HttpServletRequest request, Map<String, Object> map) throws UnsupportedEncodingException {
        Map<String, Object> parameter = new HashMap<String, Object>();
        String keyword = request.getParameter("keyword");//
        if (keyword != null && keyword != "") parameter.put("keyword", keyword);//
        int rowCountTotal = membersService.getRowSize(parameter);//
        int pageSize = 12;//
        int pageNumber = 1; //

        if (request.getParameter("pageNumber") != null && request.getParameter("pageNumber") != "") {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));//
        }
        parameter.put("limit", pageSize);//
        parameter.put("start", (pageNumber - 1) * pageSize);//
        List<Members> membersList = membersService.getAll(parameter);

        Map<String, Object> pager = new HashMap<String, Object>();
        int pageCount = 1;
        
        if (rowCountTotal % pageSize == 0) {
            pageCount = rowCountTotal / pageSize;
        } else {
            pageCount = rowCountTotal / pageSize + 1;
        }
        pager.put("pageNumber", pageNumber);
        pager.put("pageCount", pageCount);
        pager.put("rowCountTotal", rowCountTotal);

        map.put("keyword", keyword);
        map.put("pager", pager);

        map.put("membersList", membersList);
        return ResultUtil.ok().data(map);
    }


    @RequestMapping("getById.action")

    @NoLoginInterface 
    @ResponseBody
    public ResultUtil getMembersById(String id, Map<String, Object> map) {
        Members members = membersService.getById(id);
        map.put("members", members);
        return ResultUtil.ok().data(map);
    }


}
