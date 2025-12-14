package com.entity;

public class JwtEntityManager {

    private String managerid;


    private String username;


    private String realname;


    private Long time;


    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }


    public String getManagerid() {
        return managerid;
    }

    public void setManagerid(String managerid) {
        this.managerid = managerid;
    }


    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
