package com.raisac.plapp;
public class PlaylistUploadInfo {

    public String songName;

    public String songURL;

    public PlaylistUploadInfo() {

    }

    public PlaylistUploadInfo(String name, String url) {

        this.songName = name;
        this.songURL= url;
    }

    public String getImageName() {
        return songName;
    }

    public String getImageURL() {
        return songName;
    }

}
