package com.dao;


import com.entity.Projectinfo;
import java.util.List;
import java.util.Map;



public interface ProjectinfoDAO {

    void insert(Projectinfo projectinfo) ;

    void update(Projectinfo projectinfo);

    void delete(String projectinfoid) ;

    List<Projectinfo> getAll(Map<String, Object> params)  ;

    int getRowSize(Map<String, Object> params) ;

    List<Projectinfo> getTop();

    List<Projectinfo> getFlv();

    List<Projectinfo> getByProjectinfo_type(String projectinfo_typeid) ;

    List<Projectinfo> getByCond(Projectinfo projectinfo) ;

    List<Projectinfo> getByLike(Projectinfo projectinfo) ;

    Projectinfo getById(String projectinfoid);



}
