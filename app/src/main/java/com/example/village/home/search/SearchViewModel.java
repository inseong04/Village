package com.example.village.home.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class SearchViewModel extends ViewModel {
    protected MutableLiveData<Integer> position = new MutableLiveData<>();
    protected MutableLiveData<Boolean> workRemove = new MutableLiveData<>();
    protected MutableLiveData<ArrayList<String>> searchWord = new MutableLiveData<>();
    ArrayList<String> arrayList = new ArrayList<>();
    public SearchViewModel() {
        arrayList.add("");
        position.setValue(0);
        workRemove.setValue(false);
        searchWord.setValue(arrayList);
    }

    public void setPosition(int externalPosition) {
        position.setValue(externalPosition);
    }

    public void setSearchWord(String word) {
        arrayList.add(word);
        searchWord.setValue(arrayList);
    }

    public void removeSearchWord(int position) {
        arrayList.remove(position);
        searchWord.setValue(arrayList);
    }

    public void setWorkRemoveTrue() {
        workRemove.setValue(true);
    }

    public void setWorkRemoveFalse() {
        workRemove.setValue(false);
    }
}
