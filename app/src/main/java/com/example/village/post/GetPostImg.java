package com.example.village.post;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class GetPostImg extends Thread{

    PostViewModel viewModel;
    StorageReference storageReference;
    int postNumber;
    int imageNumber;

    public GetPostImg(PostViewModel viewModel,int postNumber, int imageNumber) {
        super();
        this.viewModel = viewModel;
        this.postNumber = postNumber;
        this.imageNumber = imageNumber;
        this.storageReference = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public void run() {
        super.run();

        for (int i=0; i< imageNumber; i++) {
            storageReference.child("postImg/" + "img" + "-" + postNumber + "-" + i).getDownloadUrl().
                    addOnSuccessListener(uri -> {
                        viewModel.uriArrayList.add(uri);

                    });
        }

    }
}
