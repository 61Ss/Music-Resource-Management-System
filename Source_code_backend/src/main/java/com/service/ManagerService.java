package com.service;

import com.dao.ManagerDAO;
import com.entity.Manager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("manager")
public class ManagerService {

    @Resource
    private ManagerDAO managerDao;

    public void insert(Manager manager) {
        managerDao.insert(manager);
    }

 
    public void update(Manager manager) {
        managerDao.update(manager);
    }

  
    public void delete(String managerid) {
        managerDao.delete(managerid);
    }

  
    public List<Manager> getAll(Map<String, Object> params) {
        return managerDao.getAll(params);
    }

  
    public int getRowSize(Map<String, Object> params) {
        return managerDao.getRowSize(params);
    }


  
    public List<Manager> getByCond(Manager manager) {
        return managerDao.getByCond(manager);
    }

  
    public List<Manager> getByLike(Manager manager) {
        return managerDao.getByLike(manager);
    }

 
    public Manager getById(String managerid) {
        return managerDao.getById(managerid);
    }


}
