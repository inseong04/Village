package com.example.village.home.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class SearchViewModel extends ViewModel {
    protected MutableLiveData<Integer> position = new MutableLiveData<>();
    protected MutableLiveData<Boolean> workRemove = new MutableLiveData<>();
    protected MutableLiveData<ArrayList<String>> searchWord = new MutableLiveData<>();
    protected MutableLiveData<String> searchEtvText = new MutableLiveData<>();
    protected MutableLiveData<Boolean> searchWordDeleteIndex0 = new MutableLiveData<>();
    protected MutableLiveData<Boolean> emptyAlarm = new MutableLiveData<>();
    protected Boolean first;

    ArrayList<String> arrayList = new ArrayList<>();
    public SearchViewModel() {
        position.setValue(0);
        workRemove.setValue(false);
        searchWordDeleteIndex0.setValue(false);
        first = false;
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

    public void setSearchEtvText(String word) {
        searchEtvText.setValue(word);
    }

    public void setWorkRemoveTrue() {
        workRemove.setValue(true);
    }

    public void setWorkRemoveFalse() {
        workRemove.setValue(false);
    }
}
