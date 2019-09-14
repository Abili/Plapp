package com.raisac.plapp;

import android.support.v7.widget.RecyclerView;

public class socialTabModel {
    private String Username;
    private int profileImageUrl;
    private String timePosted;
    private String Caption;
    private String comments;
    private String likes;
    private String rates;
    private String recView;


    public socialTabModel(String s1, String s) {
        Caption = s;
        this.recView = s1;
    }

    public socialTabModel(String username, String likes, String comments,
                          String rates, String pstinfTime, String caption,
                          int dphoto, String recView) {

        Username = username;
        this.profileImageUrl = dphoto;
        this.timePosted = pstinfTime;
        this.Caption = caption;
        this.comments = comments;
        this.likes = likes;
        this.rates = rates;
        this.recView = recView;

    }

    public String getRecView() {
        return recView;
    }

    public void setRecView(String recView) {
        this.recView = recView;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public int getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(int profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getTimePosted() {
        return timePosted;
    }

    public void setTimePosted(String timePosted) {
        this.timePosted = timePosted;
    }

    public String getCaption() {
        return Caption;
    }

    public void setCaption(String caption) {
        Caption = caption;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getRates() {
        return rates;
    }

    public void setRates(String rates) {
        this.rates = rates;
    }
}
