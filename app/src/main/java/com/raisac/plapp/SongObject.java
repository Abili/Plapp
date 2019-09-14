package com.raisac.plapp;


import java.io.Serializable;
import java.util.ArrayList;

public class SongObject {
    private ArrayList<SongInfo> songUrl;
    private String PlayName;
    private String UserName;

    public SongObject() {
    }

    public SongObject(ArrayList<SongInfo> songUrl, String PlayName, String UserName) {
        this.songUrl = songUrl;
        this.PlayName =PlayName;
        this.UserName=UserName;
    }

    public String getPlayName() {
        return PlayName;
    }

    public void setPlayName(String playName) {
        PlayName = playName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public ArrayList<SongInfo> getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(ArrayList<SongInfo> songUrl) {
        this.songUrl = songUrl;
    }
}