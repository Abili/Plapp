package com.raisac.plapp;

public class PlaylistNameModel {

    public PlaylistNameModel() {
    }

    private String plName, userId, username, pln;

    public PlaylistNameModel( String userId, String username,String plName) {

        this.userId =userId;
        this.username =username;
        this.plName = plName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPln() {
        return pln;
    }

    public void setPln(String pln) {
        this.pln = pln;
    }

    public String getPlName() {
        return plName;
    }

    public void setPlName(String plName) {
        this.plName = plName;
    }
}