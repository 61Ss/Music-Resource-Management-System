package com.service;

import com.dao.ProjectinfoDAO;
 import com.entity.Projectinfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("projectinfo")
public class ProjectinfoService {

    @Resource
    private ProjectinfoDAO projectinfoDao;


    public void insert(Projectinfo projectinfo) {
          projectinfoDao.insert(  projectinfo);
    }


    public void update(Projectinfo projectinfo) {
        projectinfoDao.update(  projectinfo);
    }


    public void delete(String projectinfoid) {
        projectinfoDao.delete(  projectinfoid);
    }


    public List<Projectinfo> getAll(Map<String, Object> params) {
        return projectinfoDao.getAll( params);
    }


    public int getRowSize(Map<String, Object> params) {
        return projectinfoDao.getRowSize( params);
    }


    public List<Projectinfo> getByProjectinfo_type(String projectinfo_typeid) {
        return projectinfoDao.getByProjectinfo_type(  projectinfo_typeid);
    }

    public List<Projectinfo> getTop() {
        return projectinfoDao.getTop();
    }

    public List<Projectinfo> getFlv() {
        return projectinfoDao.getFlv();
    }


    public Projectinfo getById(String projectinfoid) {
        return projectinfoDao.getById( projectinfoid);
    }



}
