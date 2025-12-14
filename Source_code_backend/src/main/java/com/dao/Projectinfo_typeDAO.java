package com.dao;

import com.entity.Projectinfo_type;

import java.util.List;
import java.util.Map;

public interface Projectinfo_typeDAO {

    void insert(Projectinfo_type projectinfo_type);

    void update(Projectinfo_type projectinfo_type) ;

    void delete(String projectinfo_typeid) ;

    List<Projectinfo_type> getAll(Map<String, Object> params) ;

    int getRowSize(Map<String, Object> params) ;

    List<Projectinfo_type> getByCond(Projectinfo_type projectinfo_type) ;

    List<Projectinfo_type> getByLike(Projectinfo_type projectinfo_type) ;

    Projectinfo_type getById(String projectinfo_typeid);

}
