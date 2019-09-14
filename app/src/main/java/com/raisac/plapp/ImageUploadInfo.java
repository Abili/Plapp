package com.raisac.plapp;

public class ImageUploadInfo {

    public String username;

    public String imageURL;

    public ImageUploadInfo() {

    }

    public ImageUploadInfo(String name, String url) {

        this.username = name;
        this.imageURL= url;
    }

    public String getUsername() {
        return username;
    }

    public String getImageURL() {
        return imageURL;
    }

}


