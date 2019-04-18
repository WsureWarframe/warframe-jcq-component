package com.example.entity;

import com.example.Demo;

import java.util.Map;

public class RivenMarketItem {
    /**
     * RivenMarket商品实体类
     * <div class="riven "
     *     id="riven_443176"
     *     data-weapon="Flux_Rifle"
     *     data-wtype="Primary"
     *     data-name="Acrisus"
     *     data-price="45"
     *     data-user="70191"
     *     data-rank="0"
     *     data-mr="13"
     *     data-age="&gt; 1 day"
     *     data-polarity="naramon"
     *     data-rerolls="0"
     *     data-stat1="CritDmg"
     *     data-stat1val="27.1"
     *     data-stat2="Slash"
     *     data-stat2val="25.8"
     *     data-stat3=""
     *     data-stat3val="0.0"
     *     data-stat4="Grineer"
     *     data-stat4val="4.1">
     */

    /**
     * id
     */
    private String id;

    /**
     * 名称
     */
    private String weapon;
    private String wtype;
    private String name;
    private Integer price;
    private String age;
    private Integer rank;
    private Integer mr;
    private Integer rerolls;
    private String polarity;

    private String stat1;
    private Double stat1val;
    private String stat2;
    private Double stat2val;
    private String stat3;
    private Double stat3val;
    private String stat4;
    private Double stat4val;

    private String seller;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWeapon() {
        return weapon;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    public String getWtype() {
        return wtype;
    }

    public void setWtype(String wtype) {
        this.wtype = wtype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getMr() {
        return mr;
    }

    public void setMr(Integer mr) {
        this.mr = mr;
    }

    public Integer getRerolls() {
        return rerolls;
    }

    public void setRerolls(Integer rerolls) {
        this.rerolls = rerolls;
    }

    public String getPolarity() {
        return polarity;
    }

    public void setPolarity(String polarity) {
        this.polarity = polarity;
    }

    public String getStat1() {
        return stat1;
    }

    public void setStat1(String stat1) {
        this.stat1 = stat1;
    }

    public Double getStat1val() {
        return stat1val;
    }

    public void setStat1val(Double stat1val) {
        this.stat1val = stat1val;
    }

    public String getStat2() {
        return stat2;
    }

    public void setStat2(String stat2) {
        this.stat2 = stat2;
    }

    public Double getStat2val() {
        return stat2val;
    }

    public void setStat2val(Double stat2val) {
        this.stat2val = stat2val;
    }

    public String getStat3() {
        return stat3;
    }

    public void setStat3(String stat3) {
        this.stat3 = stat3;
    }

    public Double getStat3val() {
        return stat3val;
    }

    public void setStat3val(Double stat3val) {
        this.stat3val = stat3val;
    }

    public String getStat4() {
        return stat4;
    }

    public void setStat4(String stat4) {
        this.stat4 = stat4;
    }

    public Double getStat4val() {
        return stat4val;
    }

    public void setStat4val(Double stat4val) {
        this.stat4val = stat4val;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RivenMarketItem{" +
                "id='" + id + '\'' +
                ", weapon='" + weapon + '\'' +
                ", wtype='" + wtype + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", age='" + age + '\'' +
                ", rank=" + rank +
                ", mr=" + mr +
                ", rerolls=" + rerolls +
                ", polarity='" + polarity + '\'' +
                ", stat1='" + stat1 + '\'' +
                ", stat1val=" + stat1val +
                ", stat2='" + stat2 + '\'' +
                ", stat2val=" + stat2val +
                ", stat3='" + stat3 + '\'' +
                ", stat3val=" + stat3val +
                ", stat4='" + stat4 + '\'' +
                ", stat4val=" + stat4val +
                ", seller='" + seller + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public String fullInfoString(Map reProperty){
        StringBuffer sb = new StringBuffer();
        sb      .append("\n"+weapon+" "+name)
                .append("("+price+"p) "+age)
                .append("\n"+rerolls+"洗 "+rank+"级 段位"+mr)
                .append("\n  "+((Map)reProperty.get(stat1)).get("Name").toString()+":"+((Map)reProperty.get(stat1)).get("Pre").toString()+stat1val+((Map)reProperty.get(stat1)).get("Unit").toString())
                .append("\n  "+((Map)reProperty.get(stat2)).get("Name").toString()+":"+((Map)reProperty.get(stat2)).get("Pre").toString()+stat2val+((Map)reProperty.get(stat2)).get("Unit").toString());
        if(stat3!=null)
            sb.append("\n  "+((Map)reProperty.get(stat3)).get("Name").toString()+":"+((Map)reProperty.get(stat3)).get("Pre").toString()+stat3val+((Map)reProperty.get(stat3)).get("Unit").toString());
        if(stat4!=null)
            sb.append("\n  "+((Map)reProperty.get(stat4)).get("Name").toString()+":"+(((Map)reProperty.get(stat4)).get("Pre").toString().equals("+")?"-":"+")+stat4val+((Map)reProperty.get(stat4)).get("Unit").toString());
        sb.append("\nid:"+seller+"["+status+"]");
        return sb.toString();
    }
}
