package com.service;

import com.dao.MembersDAO;
import com.entity.Members;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("members")

public class MembersService {
    @Resource
    private MembersDAO membersDao;

   
    public void insert(Members members) {
        membersDao.insert(members);
    }

  
    public void update(Members members) {
        membersDao.update(members);
    }


    public void delete(String membersid) {
        membersDao.delete(membersid);
    }

   
    public List<Members> getAll(Map<String, Object> params) {
        return membersDao.getAll(params);
    }


  
    public int getRowSize(Map<String, Object> params) {
        return membersDao.getRowSize(params);
    }



    public List<Members> getByCond(Members members) {
        return membersDao.getByCond(members);
    }


   
    public List<Members> getByLike(Members members) {
        return membersDao.getByLike(members);
    }

  
    public Members getById(String membersid) {
        return membersDao.getById(membersid);
    }

}
