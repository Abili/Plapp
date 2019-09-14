package com.raisac.plapp;

public class PlaylistModule  {

    //public String UserName;
    public int count;
    public int photoId;
    public String playlistName;

    public PlaylistModule() {
    }

    public PlaylistModule(String playlistName) {
      //UserName = userName;
        this.count = count;
        this.photoId = photoId;
        this.playlistName =playlistName;
    }

    /*public String getUserName() {
        return UserName;
    }*/

    /*public void setUserName(String userName) {
        UserName = userName;
    }*/

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }
}
