package com.example.entity;

public class Rivenhave {
    private String id;

    private String rivenname;

    private Integer qq;

    private Integer roll;

    private String propertystr;

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

    public Integer getRoll() {
        return roll;
    }

    public void setRoll(Integer roll) {
        this.roll = roll;
    }

    public String getPropertystr() {
        return propertystr;
    }

    public void setPropertystr(String propertystr) {
        this.propertystr = propertystr == null ? null : propertystr.trim();
    }
}