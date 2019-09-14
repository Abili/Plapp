package com.raisac.plapp;

import android.net.Uri;

public class UsernameModule {
    String uName;
    String pTime;
    Uri ImageUrl;
    String pstNumber;

    public UsernameModule() {
    }

    public UsernameModule(String uName, String pTime, Uri ImageUrl,String pstNumber) {
        this.uName = uName;
        this.pTime = pTime;
        //this.ImageUrl =ImageUrl;
        this.pstNumber =pstNumber;
    }

    public String getPstNumber() {
        return pstNumber;
    }

    public void setPstNumber(String pstNumber) {
        this.pstNumber = pstNumber;
    }

   /* public Uri getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(Uri ImageUrl) {
        this.ImageUrl = ImageUrl;
    }
*/
    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getpTime() {
        return pTime;
    }

    public void setpTime(String pTime) {
        this.pTime = pTime;
    }
}
