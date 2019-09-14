package com.raisac.plapp;

public class Playlist_Display_Module {
    private String usaname;
    private String playname;
    private String postedTime;

    public Playlist_Display_Module() {
    }

    public Playlist_Display_Module(String usaname, String playname, String postedTime) {
        this.usaname = usaname;
        this.playname = playname;
        this.postedTime = postedTime;
    }

    public String getUsaname() {
        return usaname;
    }

    public void setUsaname(String username) {
        this.usaname = username;
    }

    public String getPlayname() {
        return playname;
    }

    public void setPlaylname(String playname) {
        this.playname = playname;
    }

    public String getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(String postedTime) {
        this.postedTime = postedTime;
    }
}
