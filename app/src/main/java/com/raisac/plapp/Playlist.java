package com.raisac.plapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.List;

public class Playlist implements Parcelable {

    private String Uid;
    private String playlist_id;
    private String play_name;
    private String songUrl;
    private String time_stamp;
    private String profile_image;
    private List<Songs> songs;


    public Playlist() {
    }

    public Playlist(String play_name,String songUrl,String uid,  String time_stamp, String profile_image,List<Songs> songs, String playlist_id) {
        this.play_name = play_name;
        this.songUrl = songUrl;
        this.Uid = uid;
        this.time_stamp=time_stamp;
        this.profile_image=profile_image;
        this.songs = songs;
        this.playlist_id = playlist_id;
    }

    protected Playlist(Parcel in) {
        Uid = in.readString();
        play_name = in.readString();
        songUrl = in.readString();
        time_stamp = in.readString();
        profile_image = in.readString();
        playlist_id = in.readString();
    }

    public static final Creator<Playlist> CREATOR = new Creator<Playlist>() {
        @Override
        public Playlist createFromParcel(Parcel in) {
            return new Playlist(in);
        }

        @Override
        public Playlist[] newArray(int size) {
            return new Playlist[size];
        }
    };

    public String getId() {
        return Uid;
    }

    public void setId(String Uid) {
        this.Uid = Uid;
    }


    public String getSongUrl() {
        return songUrl;
    }


    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

    public String getPlay_name() {
        return play_name;
    }

    public void setPlay_name(String play_name) {
        this.play_name = play_name;
    }

    public void setUid(String Uid) {
        this.Uid = Uid;
    }

    public String getUid() {
        return Uid;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getPlaylist_id() {
        return playlist_id;
    }

    public void setPlaylist_id(String playlist_id) {
        this.playlist_id = playlist_id;
    }

    public List<Songs> getSongs() {
        return songs;
    }

    public void setSongs(List<Songs> songs) {
        this.songs = songs;
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "Uid='" + Uid + '\'' +
                ", play_name='" + play_name + '\'' +
                ", songUrl='" + songUrl + '\'' +
                ", time_stamp='" + time_stamp + '\'' +
                ", profile_image='" + profile_image + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(Uid);
        parcel.writeString(play_name);
        parcel.writeString(songUrl);
        parcel.writeString(time_stamp);
        parcel.writeString(profile_image);

    }
}
