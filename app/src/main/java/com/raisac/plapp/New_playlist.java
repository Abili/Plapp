package com.raisac.plapp;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class New_playlist {
    String playlist_name;
    String Username;
    String Uid;

    public New_playlist() {
    }

    public New_playlist(String playlist_name, String username, String uid) {
        this.playlist_name = playlist_name;
        Username = username;
        Uid = uid;
    }

//    public String getPlaylist_name() {
//        return playlist_name;
//    }
//
//    public void setPlaylist_name(String playlist_name) {
//        this.playlist_name = playlist_name;
//    }
//
//    public String getUsername() {
//        return Username;
//    }
//
//    public void setUsername(String username) {
//        Username = username;
//    }
    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> results = new HashMap<>();
        results.put("uid", Uid);
        results.put("author", Username);
        results.put("playlist_name", playlist_name);
        return  results;
    }

}
