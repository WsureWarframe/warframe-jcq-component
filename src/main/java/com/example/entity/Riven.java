package com.example.entity;

import org.codehaus.jackson.annotate.JsonProperty;

public class Riven {
    /***
     * 样例：
     *       "Id": 1,
     *       "Name": "DEX 达克拉双剑",
     *       "Type": "Melee",
     *       "Level": 3,
     *       "Ratio": 1.15
     */
    @JsonProperty("Id")
    private Integer Id;
    @JsonProperty("Name")
    private String Name;
    @JsonProperty("Type")
    private String Type;
    @JsonProperty("Level")
    private Integer Level;
    @JsonProperty("Ratio")
    private Double Ratio;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public Integer getLevel() {
        return Level;
    }

    public void setLevel(Integer level) {
        Level = level;
    }

    public Double getRatio() {
        return Ratio;
    }

    public void setRatio(Double ratio) {
        Ratio = ratio;
    }

    @Override
    public String toString() {
        return "Riven{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                ", Type='" + Type + '\'' +
                ", Level=" + Level +
                ", Ratio=" + Ratio +
                '}';
    }
}
