package com.entity;

import java.util.ArrayList;
import java.util.List;

public class Projectinfo_type {
    private String projectinfo_typeid;
    private String projectinfo_typename;


    private List<Projectinfo> projectinfoList = new ArrayList<Projectinfo>();

    public List<Projectinfo> getProjectinfoList() {
        return projectinfoList;
    }

    public void setProjectinfoList(List<Projectinfo> projectinfoList) {
        this.projectinfoList = projectinfoList;
    }



    public String getProjectinfo_typeid() {
        return projectinfo_typeid;
    }

    public void setProjectinfo_typeid(String projectinfo_typeid) {
        this.projectinfo_typeid = projectinfo_typeid;
    }

    public String getProjectinfo_typename() {
        return this.projectinfo_typename;
    }

    public void setProjectinfo_typename(String projectinfo_typename) {
        this.projectinfo_typename = projectinfo_typename;
    }
}
