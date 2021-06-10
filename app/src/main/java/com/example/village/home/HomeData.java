package com.example.village.home;

import android.net.Uri;

public class HomeData {
    Uri HomeImageuri;
    int postNum;
    String title;
    String location;
    String price;

    public HomeData(Uri homeImageuri, int postNum, String title, String location, String price) {
        HomeImageuri = homeImageuri;
        this.postNum = postNum;
        this.title = title;
        this.location = location;
        this.price = price;
    }

    public Uri getHomeImageuri() {
        return HomeImageuri;
    }

    public void setHomeImageuri(Uri homeImageuri) {
        HomeImageuri = homeImageuri;
    }

    public int getPostNum() {
        return postNum;
    }

    public void setPostNum(int postNum) {
        this.postNum = postNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
