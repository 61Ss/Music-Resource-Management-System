package com.dao;

import com.entity.Members;

import java.util.List;
import java.util.Map;

public interface MembersDAO {

    void insert(Members members);


    void update(Members members);


    void delete(String membersid);


    List<Members> getAll(Map<String, Object> params);



    int getRowSize(Map<String, Object> params);



    List<Members> getByCond(Members members);


    List<Members> getByLike(Members members);

    Members getById(String membersid);



}
