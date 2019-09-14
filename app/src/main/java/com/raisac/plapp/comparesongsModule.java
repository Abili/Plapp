package com.raisac.plapp;

public class comparesongsModule {
    String songname;
    String artistname;
    String count1;
    String count2;
    String songname2;
    String artistname2;
    String timeofPosting;

    public comparesongsModule() {
    }

    public comparesongsModule(String songname, String artistname, String count1,
                              String count2, String songname2, String artistname2, String timeofPosting) {
        this.songname = songname;
        this.artistname = artistname;
        this.count1 = count1;
        this.count2 = count2;
        this.songname2 = songname2;
        this.artistname2 = artistname2;
        this.timeofPosting = timeofPosting;
    }

    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public String getArtistname() {
        return artistname;
    }

    public void setArtistname(String artistname) {
        this.artistname = artistname;
    }

    public String getCount1() {
        return count1;
    }

    public void setCount1(String count1) {
        this.count1 = count1;
    }

    public String getCount2() {
        return count2;
    }

    public void setCount2(String count2) {
        this.count2 = count2;
    }

    public String getSongname2() {
        return songname2;
    }

    public void setSongname2(String songname2) {
        this.songname2 = songname2;
    }

    public String getArtistname2() {
        return artistname2;
    }

    public void setArtistname2(String artistname2) {
        this.artistname2 = artistname2;
    }

    public String getTimeofPosting() {
        return timeofPosting;
    }

    public void setTimeofPosting(String timeofPosting) {
        this.timeofPosting = timeofPosting;
    }
}
