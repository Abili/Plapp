package com.raisac.plapp;

public class Like {

    String PlaylistId;
    String UserId;

    public Like() {
    }

    public Like(String playlistId, String userId) {
        PlaylistId = playlistId;
        UserId = userId;
    }

    public String getPlaylistId() {
        return PlaylistId;
    }

    public void setPlaylistId(String playlistId) {
        PlaylistId = playlistId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
