package com.example.village.home;

import android.net.Uri;

public class HomeData {
    Uri HomeImageuri;
    String title;
    String location;
    String price;

    public Uri getHomeImageuri() {
        return HomeImageuri;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getPrice() {
        return price;
    }

   public HomeData(Uri uri) {
        this.HomeImageuri = uri;
   }

    public HomeData(String title, String location, String price) {
        this.title = title;
        this.location = location;
        this.price = price;
    }

    public HomeData(Uri uri, String title, String location, String price) {
        this.HomeImageuri = uri;
        this.title = title;
        this.location = location;
        this.price = price;
    }
}
