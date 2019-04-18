package com.example.entity;

public class Rivendaily {
    private String id;

    private String rivenname;

    private Integer qq;

    private String propertystr;

    private Integer time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRivenname() {
        return rivenname;
    }

    public void setRivenname(String rivenname) {
        this.rivenname = rivenname == null ? null : rivenname.trim();
    }

    public Integer getQq() {
        return qq;
    }

    public void setQq(Integer qq) {
        this.qq = qq;
    }

    public String getPropertystr() {
        return propertystr;
    }

    public void setPropertystr(String propertystr) {
        this.propertystr = propertystr == null ? null : propertystr.trim();
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}