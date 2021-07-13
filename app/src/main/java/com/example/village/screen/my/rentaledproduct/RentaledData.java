package com.example.village.screen.my.rentaledproduct;

import android.net.Uri;

public class RentaledData {

    protected String postNum;
    protected Uri productUri;
    protected String title;
    protected String location;
    protected String price;

    public RentaledData(String postNum, Uri productUri, String title, String location, String price) {
        this.postNum = postNum;
        this.productUri = productUri;
        this.title = title;
        this.location = location;
        this.price = price;
    }

    public String getPostNum() {
        return postNum;
    }

    public void setPostNum(String postNum) {
        this.postNum = postNum;
    }

    public Uri getProductUri() {
        return productUri;
    }

    public void setProductUri(Uri productUri) {
        this.productUri = productUri;
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
