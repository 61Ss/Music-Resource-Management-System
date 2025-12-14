package com.service;

import com.dao.Projectinfo_typeDAO;
import com.entity.Projectinfo_type;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("projectinfo_type")

public class Projectinfo_typeService {
    @Resource
    private Projectinfo_typeDAO projectinfo_typeDao;


    public void insert(Projectinfo_type projectinfo_type) {
        projectinfo_typeDao.insert( projectinfo_type);
    }


    public void update(Projectinfo_type projectinfo_type) {
        projectinfo_typeDao.update(  projectinfo_type);
    }


    public void delete(String projectinfo_typeid) {
        projectinfo_typeDao.delete(  projectinfo_typeid);
    }


    public List<Projectinfo_type> getAll(Map<String, Object> params) {
        return projectinfo_typeDao.getAll( params);
    }

    public int getRowSize(Map<String, Object> params) {
        return projectinfo_typeDao.getRowSize( params);
    }




   
    public List<Projectinfo_type> getByCond(Projectinfo_type projectinfo_type) {
        return projectinfo_typeDao.getByCond(  projectinfo_type);
    }

   
    public List<Projectinfo_type> getByLike(Projectinfo_type projectinfo_type) {
        return projectinfo_typeDao.getByLike(  projectinfo_type);
    }

  
    public Projectinfo_type getById(String projectinfo_typeid) {
        return projectinfo_typeDao.getById(  projectinfo_typeid);
    }

}
