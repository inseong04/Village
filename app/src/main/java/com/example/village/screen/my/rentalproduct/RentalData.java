package com.example.village.screen.my.rentalproduct;

import android.net.Uri;

public class RentalData {
    protected int postNum;
    protected Uri productUri;
    protected String title;
    protected String location;
    protected String price;
    protected Boolean rental;

    public RentalData(int postNum, Uri productUri, String title, String location, String price, Boolean rental) {
        this.postNum = postNum;
        this.productUri = productUri;
        this.title = title;
        this.location = location;
        this.price = price;
        this.rental = rental;
    }

    public int getPostNum() {
        return postNum;
    }

    public void setPostNum(int postNum) {
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

    public Boolean getRental() {
        return rental;
    }

    public void setRental(Boolean rental) {
        this.rental = rental;
    }
}
