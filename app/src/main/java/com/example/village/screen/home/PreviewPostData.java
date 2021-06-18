package com.example.village.screen.home;

import android.net.Uri;

public class PreviewPostData {
    public Uri HomeImageuri;
    public int postNum;
    public String title;
    public String location;
    public String price;
    public Boolean rental;

    public PreviewPostData(Uri homeImageuri, int postNum, String title, String location, String price, Boolean rental) {
        HomeImageuri = homeImageuri;
        this.postNum = postNum;
        this.title = title;
        this.location = location;
        this.price = price;
        this.rental = rental;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
