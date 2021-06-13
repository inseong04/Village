package com.example.village.screen.post;

import android.util.Log;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class GetPostImgThread extends Thread {
    PostViewModel viewModel;
    StorageReference storageReference;
    int postNumber;
    int imageNumber;
    public GetPostImgThread (PostViewModel viewModel, int postNumber, int imageNumber) {
        this.viewModel = viewModel;
        this.postNumber = postNumber;
        this.imageNumber = imageNumber;
        storageReference = FirebaseStorage.getInstance().getReference();

    }

    @Override
    public void run() {
        super.run();
        for(int i=0; i< imageNumber; i++) {
            Log.e("postimg","postImg/" + "img" + "-" + postNumber + "-" + i);
            storageReference.child("postImg/" + "img" + "-" + postNumber + "-" + i).getDownloadUrl().
                    addOnSuccessListener(uri -> {
                        Log.e("asdgf","qagwer");
                        viewModel.uriArrayList.add(uri);
                    });
        }
        // 다운로드 전에 스레드 종료됨. 해결해야합니다.
/*        for(int i=0; i< imageNumber; i++) {
            Log.e("array", viewModel.uriArrayList.get(i).toString());
        }*/
    }
}
