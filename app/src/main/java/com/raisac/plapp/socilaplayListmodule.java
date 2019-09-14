package com.raisac.plapp;

public class socilaplayListmodule {
    String songsocialname;
    String Artistsocialname;
    String songsocialduration;

    public socilaplayListmodule(String name) {
        this.songsocialname = name;
    }

    public socilaplayListmodule(String songsocialname, String artistsocialname, String songsocialduration) {

        Artistsocialname = Artistsocialname;
        this.songsocialduration = songsocialduration;
    }

    public String getSongsocialname() {
        return songsocialname;
    }

    public void setSongsocialname(String songsocialname) {
        this.songsocialname = songsocialname;
    }

    public String getArtistsocialname() {
        return Artistsocialname;
    }

    public void setArtistsocialname(String artistsocialname) {
        Artistsocialname = artistsocialname;
    }

    public String getSongsocialduration() {
        return songsocialduration;
    }

    public void setSongsocialduration(String songsocialduration) {
        this.songsocialduration = songsocialduration;
    }
}
