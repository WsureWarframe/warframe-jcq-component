package com.example.entity;

public class Sale {

    private String Search;

    private String Zh;

    private String En;

    public String getSearch() {
        return Search;
    }

    public void setSearch(String search) {
        Search = search;
    }

    public String getZh() {
        return Zh;
    }

    public void setZh(String zh) {
        Zh = zh;
    }

    public String getEn() {
        return En;
    }

    public void setEn(String en) {
        En = en;
    }

    public Sale(String search, String zh, String en) {
        this.Search = search;
        this.Zh = zh;
        this.En = en;
    }
}
