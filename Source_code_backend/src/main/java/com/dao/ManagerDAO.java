package com.dao;

import com.entity.Manager;

import java.util.List;
import java.util.Map;

public interface ManagerDAO {

    
    void insert(Manager manager);

    void update(Manager manager);


    void delete(String managerid);

    List<Manager> getAll(Map<String, Object> params);

    int getRowSize(Map<String, Object> params);


    List<Manager> getByCond(Manager manager);


    List<Manager> getByLike(Manager manager);


    Manager getById(String managerid);


}
