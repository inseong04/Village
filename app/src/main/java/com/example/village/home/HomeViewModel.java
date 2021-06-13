package com.example.village.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {
    public MutableLiveData<Boolean> recentSearchVisible = new MutableLiveData<>();
    public MutableLiveData<Boolean> notFocusVisible = new MutableLiveData<>();
    public MutableLiveData<Boolean> focusVisible = new MutableLiveData<>();
/*    public ArrayList<HomeData> arrayList;*/
    protected ArrayList<PreviewPostData> productArray;
    /*    public MutableLiveData<ArrayList<HomeData>> product = new MutableLiveData<>();*/
    public MutableLiveData<ArrayList<PreviewPostData>> arrayListMutableLiveData = new MutableLiveData<>();
    private ArrayList<PreviewPostData> arrayList = new ArrayList<>();
    protected int postNumber;
/*    public ArrayList<HomeData> getArrayList() {
        return arrayList;
    }*/

    public int getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(int postNumber) {
        this.postNumber = postNumber;
    }

    public ArrayList<PreviewPostData> getProductArray() {
        return productArray;
    }

    public void setProductArray(ArrayList<PreviewPostData> productArray) {
        this.productArray = productArray;
    }
/*    public void setProduct() {
        this.product = arrayListMutableLiveData;
    }

    public MutableLiveData<ArrayList<HomeData>> getProduct() {
        return product;
    }*/

    public void setArrayListMutableLiveData(PreviewPostData previewPostData) {
        arrayList.add(previewPostData);
        this.arrayListMutableLiveData.setValue(arrayList);
    }

    public HomeViewModel() {
        recentSearchVisible.setValue(false);
        notFocusVisible.setValue(true);
        focusVisible.setValue(false);
        this.productArray = new ArrayList<>();
        this.postNumber = 0;
    }


}
