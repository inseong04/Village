package com.example.village;

import android.net.Uri;

import java.util.ArrayList;

public class RentalList {

    private Uri rentalImageIv;
    private String productTitle;
    private String sellerLocation;
    private String productPrice;

    public RentalList(Uri uri, String s, String s1, String s2) {
        this.rentalImageIv = uri;
        this.productTitle = s;
        this.sellerLocation = s1;
        this.productPrice = s2;

        
    }

    public Uri getRentalImageIv() { return rentalImageIv; }

    public void setRentalImageIv(Uri rentalImageIv) { this.rentalImageIv = rentalImageIv; }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getSellerLocation() {
        return sellerLocation;
    }

    public void setSellerLocation(String sellerLocation) {
        this.sellerLocation = sellerLocation;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
