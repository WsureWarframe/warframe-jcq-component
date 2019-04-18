package com.example.entity;

public class Rename {
    private Long qq;

    private String username;

    private String robotname;

    public Long getQq() {
        return qq;
    }

    public void setQq(Long qq) {
        this.qq = qq;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getRobotname() {
        return robotname;
    }

    public void setRobotname(String robotname) {
        this.robotname = robotname == null ? null : robotname.trim();
    }

    @Override
    public String toString() {
        return "Rename{" +
                "qq=" + qq +
                ", username='" + username + '\'' +
                ", robotname='" + robotname + '\'' +
                '}';
    }
}