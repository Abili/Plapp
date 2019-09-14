package com.raisac.plapp;

import com.google.firebase.database.Exclude;

public class Songs {
    private String song_url;
    private String user_id;
    private String timestamp;
    private String u_name;

    public Songs() {
    }

    public Songs(String song_url, String user_id, String timestamp, String u_name) {
        this.song_url = song_url;
        this.user_id = user_id;
        this.timestamp = timestamp;
        this.u_name = u_name;
    }

    public String getSong_url() {
        return song_url;
    }

    public void setSong_url(String song_url) {
        this.song_url = song_url;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    @Override
    public String toString() {
        return "Songs{" +
                "song_url='" + song_url + '\'' +
                ", user_id='" + user_id + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", u_name='" + u_name + '\'' +
                '}';
    }
}
