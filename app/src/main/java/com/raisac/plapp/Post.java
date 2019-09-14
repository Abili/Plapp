package com.raisac.plapp;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// [START post_class]
@IgnoreExtraProperties
public class Post {

    public String listName;
    public ArrayList<SongInfo> songUrl;
    public String author;

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(String listName, ArrayList<SongInfo> songUrl, String author) {
        this.listName = listName;
        this.songUrl = songUrl;
        this.author = author;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public ArrayList<SongInfo> getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(ArrayList<SongInfo> songUrl) {
        this.songUrl = songUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    // [START post_to_map]
    }
    // [END post_to_map]


// [END post_class]
