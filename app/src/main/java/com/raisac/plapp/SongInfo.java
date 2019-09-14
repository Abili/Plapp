package com.raisac.plapp;


import java.io.Serializable;

public class SongInfo implements Serializable {
    private String Songname;
    private String Artistname;
    private String SongUrl;
    String recycleview;






    public SongInfo() {
    }

    public SongInfo(String lastPathSegment) {
         Songname=lastPathSegment;
         this.Artistname = Artistname;



   /* public SongInfo(String songname, String artistname, String songUrl) {
        Songname = songname;
        Artistname = artistname;
        SongUrl = songUrl;*/

    }


    public String getRecycleview() {
        return recycleview;
    }

    public void setRecycleview(String recycleview) {
        this.recycleview = recycleview;
    }

    public String getSongname() {
        return Songname;
    }

    public void setSongname(String songname) {
        Songname = songname;
    }

    public String getArtistname() {
        return Artistname;
    }

    public void setArtistname(String artistname) {
        Artistname = artistname;
    }

    public String getSongUrl() {
        return SongUrl;
    }

    public void setSongUrl(String songUrl) {
        SongUrl = songUrl;
    }
}
