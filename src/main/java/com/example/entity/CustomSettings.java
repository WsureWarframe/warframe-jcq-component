package com.example.entity;

public class CustomSettings {

    /**
     * @robotName:机器人名字
     */
    private String robotName ;

    private String help;

    private String tip;

    private long masterQQ;

    private String alert;

    private String invasions;

    private String sortie;

    private String fissures;

    private String earthCycle;

    private String cetusCycle;

    private String voidTrader;

    private String simaris;

    private String dailyDeals;

    private String events;



    public String getRobotName() {
        return robotName;
    }

    public void setRobotName(String robotName) {
        this.robotName = robotName;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public long getMasterQQ() {
        return masterQQ;
    }

    public void setMasterQQ(long masterQQ) {
        this.masterQQ = masterQQ;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getInvasions() {
        return invasions;
    }

    public void setInvasions(String invasions) {
        this.invasions = invasions;
    }

    public String getSortie() {
        return sortie;
    }

    public void setSortie(String sortie) {
        this.sortie = sortie;
    }

    public String getFissures() {
        return fissures;
    }

    public void setFissures(String fissures) {
        this.fissures = fissures;
    }

    public String getEarthCycle() {
        return earthCycle;
    }

    public void setEarthCycle(String earthCycle) {
        this.earthCycle = earthCycle;
    }

    public String getCetusCycle() {
        return cetusCycle;
    }

    public void setCetusCycle(String cetusCycle) {
        this.cetusCycle = cetusCycle;
    }

    public String getVoidTrader() {
        return voidTrader;
    }

    public void setVoidTrader(String voidTrader) {
        this.voidTrader = voidTrader;
    }

    public String getSimaris() {
        return simaris;
    }

    public void setSimaris(String simaris) {
        this.simaris = simaris;
    }

    public String getDailyDeals() {
        return dailyDeals;
    }

    public void setDailyDeals(String dailyDeals) {
        this.dailyDeals = dailyDeals;
    }

    public String getEvents() {
        return events;
    }

    public void setEvents(String events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "CustomSettings{" +
                ", robotName='" + robotName + '\'' +
                ", help='" + help + '\'' +
                ", tip='" + tip + '\'' +
                ", masterQQ=" + masterQQ +
                ", alert='" + alert + '\'' +
                ", invasions='" + invasions + '\'' +
                ", sortie='" + sortie + '\'' +
                ", fissures='" + fissures + '\'' +
                ", earthCycle='" + earthCycle + '\'' +
                ", cetusCycle='" + cetusCycle + '\'' +
                ", voidTrader='" + voidTrader + '\'' +
                ", simaris='" + simaris + '\'' +
                ", dailyDeals='" + dailyDeals + '\'' +
                ", events='" + events + '\'' +
                '}';
    }
}
