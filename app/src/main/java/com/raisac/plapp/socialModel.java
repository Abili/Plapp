package com.raisac.plapp;

public class socialModel {
    private String Username;
    private int profileImageUrl;
    private String Caption;


    public socialModel(String caption) {
    }


    public socialModel(String username, int profileImageUrl,
                       String caption) {
        Username = username;
        this.profileImageUrl = profileImageUrl;

        this.Caption = caption;


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


    public String getCaption() {
        return Caption;
    }

    public void setCaption(String caption) {
        Caption = caption;
    }

}
