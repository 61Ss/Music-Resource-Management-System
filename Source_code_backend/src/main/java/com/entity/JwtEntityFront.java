package com.entity;

public class JwtEntityFront {

    private String userid;

    private Members members;


    private Long time;


    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }




    public Members getMembers() {
        return members;
    }

    public void setMembers(Members members) {
        this.members = members;
    }


}
