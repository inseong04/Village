package com.example.village.home.searchresult;

import androidx.lifecycle.ViewModel;

import com.example.village.home.PreviewPostData;

import java.util.ArrayList;

public class SearchResultViewModel extends ViewModel {
    ArrayList<Integer> matchingPostNum;
    ArrayList<PreviewPostData> postArrayList;

    public SearchResultViewModel() {
        this.matchingPostNum = new ArrayList<>();
        this.postArrayList = new ArrayList<>();
    }

    public ArrayList<PreviewPostData> getPostArrayList() {
        return postArrayList;
    }

    public void addMatchingPostNumber(int postNumber) {
        this.matchingPostNum.add(postNumber);
    }
}
