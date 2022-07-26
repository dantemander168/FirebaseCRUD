package com.tipmnl.firebasecrud;

public class Summoner {
    String summoner_name, key;

    public Summoner(){

    }
    public Summoner(String summoner_name, String key){
        this.summoner_name = summoner_name;
        this.key = key;
    }

    public String getSummoner_name() {
        return summoner_name;
    }

    public void setSummoner_name(String summoner_name) {
        this.summoner_name = summoner_name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
