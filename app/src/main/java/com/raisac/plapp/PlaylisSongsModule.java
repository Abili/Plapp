package com.raisac.plapp;

public class PlaylisSongsModule {
    String song_Title;
    String song_duration;
    String song_size;


    public PlaylisSongsModule() {
    }

    public PlaylisSongsModule(String song_Title) {
        this.song_Title = song_Title;
        //this.song_duration = song_duration;
        //this.song_size = song_size;
    }

    public String getSong_Title() {
        return song_Title;
    }

    public void setSong_Title(String song_Title) {
        this.song_Title = song_Title;
    }

    public String getSong_duration() {
        return song_duration;
    }

    public void setSong_duration(String song_duration) {
        this.song_duration = song_duration;
    }

    public String getSong_size() {
        return song_size;
    }

    public void setSong_size(String song_size) {
        this.song_size = song_size;
    }
}
