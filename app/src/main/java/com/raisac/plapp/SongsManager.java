package com.raisac.plapp;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore;

public class SongsManager {
    // SDCard Path
    final String MEDIA_PATH = new String(getSDPath() + "/Download");//
     private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

    // Constructor
    public SongsManager(){

    }

    public String getSDPath(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);   //判断外置SD卡是否存在
        if(sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取外置SD卡根目录
        }
        else{
            sdDir = Environment.getRootDirectory();//获取内置SD卡根目录
        }
        return sdDir.toString();
    }

    /**
     * Function to read all mp3 files from sdcard
     * and store the details in ArrayList
     * */
    public ArrayList<HashMap<String, String>> getPlayListFromFile() {
        File home = new File(MEDIA_PATH);

        System.out.println(home);

        if (home.listFiles(new FileExtensionFilter()).length > 0) {
            for (File file : home.listFiles(new FileExtensionFilter())) {
                HashMap<String, String> song = new HashMap<String, String>();
                song.put("songTitle", file.getName().substring(0, (file.getName().length() - 4)));
                song.put("songPath", file.getPath());

                // Adding each song to SongList
                songsList.add(song);
            }
        }
        else{
            HashMap<String, String> song = new HashMap<String, String>();
            song.put("songTitle","现在你没有下载歌曲噢");
            song.put("songPath","/");
            songsList.add(song);
        }
        // return songs list array
        return songsList;
    }

    /**
     * Class to filter files which are having .mp3 extension
     * */
    class FileExtensionFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".mp3") || name.endsWith(".MP3") || name.endsWith(".ogg") || name.endsWith(".OGG"));
        }
    }




    public ArrayList<HashMap<String, String>> getPlayListFromContent(Context mediaContext) {
        ArrayList<HashMap<String, String>> listAudio = new ArrayList<HashMap<String, String>>();
        Cursor cursor = mediaContext.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        System.out.println("count = " + cursor.getCount());  //获取总共有多少个条目
        while (cursor.moveToNext()) {

//      MediaStore.Audio.Media._ID,     //媒体文件的ID值
//      MediaStore.Audio.Media.DISPLAY_NAME,        //媒体文件的文件名
//      MediaStore.Audio.Media.DATA,    //媒体文件的绝对路径
//      MediaStore.Audio.Media.SIZE         //媒体文件的大小
//      System.out.println("ColumnCount = " + cursor.getColumnCount());
//      System.out.println(cursor.getString(0)); // 音频ID
//      System.out.println(cursor.getString(1)); // 音频绝对路径
//      System.out.println(cursor.getString(2)); // 音频文件名
//      System.out.println(cursor.getString(3)); // 音频的大小 字节

            HashMap<String, String> song = new HashMap<String, String>();
            song.put("songTitle", cursor.getString(2));
            song.put("songPath", cursor.getString(1));
            listAudio.add(song);
        }
        return listAudio;
    }
}
