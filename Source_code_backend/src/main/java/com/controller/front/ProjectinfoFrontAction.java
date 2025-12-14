package com.controller.front;

import com.entity.*;
import com.service.*;
// import com.util.MyWordSegmenter;

import com.util.ResultUtil;
import com.interceptor.NoLoginInterface;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

import java.text.SimpleDateFormat;


@Controller

@RequestMapping("/front")
public class ProjectinfoFrontAction extends BaseFrontAction {
  
    @Resource
    private ProjectinfoService projectinfoService;


    @Resource
    private MembersService membersService;
    @Resource
    private Projectinfo_typeService projectinfo_typeService;



    @RequestMapping("projectinfo.action")
    @NoLoginInterface   
    @ResponseBody
    public ResultUtil projectinfo(HttpServletRequest request, Map<String, Object> map) throws UnsupportedEncodingException {

        Map<String, Object> parameter = new HashMap<String, Object>();
        String keyword = request.getParameter("keyword");
        if (keyword != null && keyword != "") {

            parameter.put("keyword", keyword);//
        }
        String keyword_table = request.getParameter("keyword_table");//
        if (keyword_table != null && keyword_table != "") {

            parameter.put("keyword_table", keyword_table);//
        }

        String projectinfo_album = request.getParameter("projectinfo_album");//
        if (projectinfo_album != null && projectinfo_album != "") {
            parameter.put("projectinfo_album", projectinfo_album);//
        }
        String projectinfo_istop = request.getParameter("projectinfo_istop");//
        if (projectinfo_istop != null && projectinfo_istop != "") {
            parameter.put("projectinfo_istop", projectinfo_istop);//
        }
        String projectinfo_isflv = request.getParameter("projectinfo_isflv");//
        if (projectinfo_isflv != null && projectinfo_isflv != "") {
            parameter.put("projectinfo_isflv", projectinfo_isflv);//
        }
        String projectinfo_5 = request.getParameter("projectinfo_5");//
        if (projectinfo_5 != null && projectinfo_5 != "") {
            parameter.put("projectinfo_5", projectinfo_5);//
        }
        String projectinfo_6 = request.getParameter("projectinfo_6");//
        if (projectinfo_6 != null && projectinfo_6 != "") {
            parameter.put("projectinfo_6", projectinfo_6);//
        }


        String projectinfo_typeid = request.getParameter("projectinfo_typeid");//
        if (projectinfo_typeid != null && projectinfo_typeid != "") {

            parameter.put("projectinfo_typeid", projectinfo_typeid);//
        }

        parameter.put("iscooled", 0);//
        parameter.put("status", 0);//

        int rowCountTotal = projectinfoService.getRowSize(parameter);//
        int pageSize = 12;//
        int pageNumber = 1; //

        if (request.getParameter("pageNumber") != null && request.getParameter("pageNumber") != "") {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        }
        parameter.put("limit", pageSize);
        parameter.put("start", (pageNumber - 1) * pageSize);


        List<Projectinfo> projectinfoList = projectinfoService.getAll(parameter);


 
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
        map.put("keyword_table", keyword_table);
        map.put("pager", pager);


        map.put("projectinfoList", projectinfoList);
        return ResultUtil.ok().data(map);
    }

    @RequestMapping("projectinfoDetail.action")
    @NoLoginInterface  
    @ResponseBody
    public ResultUtil projectinfoDetail(HttpServletRequest request, String id, Map<String, Object> map) throws UnsupportedEncodingException {

        //Map<String, Object> parameter = new HashMap<String, Object>();
        Projectinfo projectinfo = projectinfoService.getById(id);


        map.put("projectinfo", projectinfo);

        if (projectinfo != null) {
            projectinfo.setProjectinfo_hits("" + (Integer.parseInt(projectinfo.getProjectinfo_hits()) + 1));
            projectinfoService.update(projectinfo);
        }

        return ResultUtil.ok().data(map);
    }


    @RequestMapping("status.action")
    @NoLoginInterface  
    @ResponseBody
    public ResultUtil status(String id, int status, Map<String, Object> map) {

        Projectinfo projectinfo = projectinfoService.getById(id);
        projectinfo.setStatus(status);
        projectinfoService.update(projectinfo);
        return ResultUtil.ok();
    }

    @RequestMapping("create.action")

    @ResponseBody
    public ResultUtil create(Map<String, Object> map) {

        Map<String, Object> parameter = new HashMap<String, Object>();
        List<Projectinfo_type> projectinfo_typeList = projectinfo_typeService.getAll(parameter);
        map.put("projectinfo_typeList", projectinfo_typeList);
        return ResultUtil.ok().data(map);
    }


    @RequestMapping("add.action")
    @ResponseBody
    public ResultUtil add(Projectinfo projectinfo) {

        Map<String, Object> parameter = new HashMap<String, Object>();


        String membersid = (String) this.getSession().getAttribute("userid");
        projectinfo.setMembersid(membersid);
        projectinfo.setIscooled(1);
        projectinfo.setStatus(0);
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = formatter.format(currentTime);
        projectinfo.setCreatedate(date);
        projectinfo.setprojectinfo_0(0);
        projectinfo.setProjectinfo_hits("0");
        projectinfo.setProjectinfo_isflv("No");
        projectinfo.setProjectinfo_istop("No");


         

        projectinfoService.insert(projectinfo);
        return ResultUtil.ok();
    }

    @RequestMapping("delete.action")
    @ResponseBody
    public ResultUtil delete(String id) {
        // Get current user ID
        String membersid = (String) this.getSession().getAttribute("userid");
        if (membersid == null || membersid.isEmpty()) {
            return ResultUtil.error().message("Please login first");
        }
        
        // Get music file information
        Projectinfo projectinfo = projectinfoService.getById(id);
        if (projectinfo == null) {
            return ResultUtil.error().message("File does not exist");
        }
        
        // Check if the file was uploaded by the user
        if (!membersid.equals(projectinfo.getMembersid())) {
            return ResultUtil.error().message("You can only delete music files that you uploaded");
        }
        
        // Execute delete operation
        projectinfoService.delete(id);
        return ResultUtil.ok().message("Delete successful");
    }


    @RequestMapping("update.action")
    @ResponseBody
    public ResultUtil update(Projectinfo projectinfo) {
        // Get current user ID
        String membersid = (String) this.getSession().getAttribute("userid");
        if (membersid == null || membersid.isEmpty()) {
            return ResultUtil.error().message("Please login first");
        }
        
        // Get original music file information
        Projectinfo originalProjectinfo = projectinfoService.getById(String.valueOf(projectinfo.getProjectinfoid()));
        if (originalProjectinfo == null) {
            return ResultUtil.error().message("File does not exist");
        }
        
        // Check if the file was uploaded by the user
        if (!membersid.equals(originalProjectinfo.getMembersid())) {
            return ResultUtil.error().message("You can only edit music files that you uploaded");
        }
        
        // Preserve original uploader ID
        projectinfo.setMembersid(originalProjectinfo.getMembersid());
        
        // Execute update operation
        projectinfoService.update(projectinfo);
        return ResultUtil.ok().message("Update successful");
    }

    @RequestMapping("getList.action")
    @ResponseBody
    public ResultUtil getList(HttpServletRequest request, Map<String, Object> map) throws UnsupportedEncodingException {

        Map<String, Object> parameter = new HashMap<String, Object>();
        String keyword = request.getParameter("keyword");
        if (keyword != null && keyword != "") {

            parameter.put("keyword", keyword);
        }
        String membersid = (String) this.getSession().getAttribute("userid");
        Members members = membersService.getById(membersid);
        parameter.put("membersid", members.getMembersid());

        int rowCountTotal = projectinfoService.getRowSize(parameter);
        int pageSize = 12;
        int pageNumber = 1; 

        if (request.getParameter("pageNumber") != null && request.getParameter("pageNumber") != "") {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        }
        parameter.put("limit", pageSize);
        parameter.put("start", (pageNumber - 1) * pageSize);


        List<Projectinfo> projectinfoList = projectinfoService.getAll(parameter);

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


        map.put("projectinfoList", projectinfoList);
        return ResultUtil.ok().data(map);//
    }


    @RequestMapping("getById.action")
    @NoLoginInterface   
    @ResponseBody
    public ResultUtil getById(String id, Map<String, Object> map) {

        Map<String, Object> parameter = new HashMap<String, Object>();
        Projectinfo projectinfo = projectinfoService.getById(id);
        map.put("projectinfo", projectinfo);
        List<Members> membersList = membersService.getAll(parameter);
        map.put("membersList", membersList);
        List<Projectinfo_type> projectinfo_typeList = projectinfo_typeService.getAll(parameter);
        map.put("projectinfo_typeList", projectinfo_typeList);
        return ResultUtil.ok().data(map);
    }


    public int getRandom(int min, int max) {

        Random random = new Random();

        return random.nextInt(max - min + 1) + min;

    }


}
