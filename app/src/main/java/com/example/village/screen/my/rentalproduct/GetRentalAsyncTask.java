package com.example.village.screen.my.rentalproduct;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.village.databinding.ActivityRentalProductBinding;
import com.example.village.screen.home.PreviewPostData;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class GetRentalAsyncTask extends AsyncTask {

    private final RentalProductViewModel viewModel;
    private final ActivityRentalProductBinding binding;
    private final String[] rentalNumberArray;
    private StorageReference storageReference;
    private int count;
    GetRentalAsyncTask(RentalProductViewModel viewModel, ActivityRentalProductBinding binding,
                        String[] rentalNumberArray) {
        this.viewModel = viewModel;
        this.binding = binding;
        this.rentalNumberArray = rentalNumberArray;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        storageReference = FirebaseStorage.getInstance().getReference();
        count = 0;
    }
    @Override
    protected Object doInBackground(Object[] objects) {

        if (count < rentalNumberArray.length) {
            FirebaseFirestore.getInstance().collection("post")
                    .document(rentalNumberArray[count])
                    .get()
                    .addOnCompleteListener(task -> {
                        DocumentSnapshot snapshot = task.getResult();
                        storageReference.child("postImg/img-" + rentalNumberArray[count] + "-0").getDownloadUrl().
                                addOnSuccessListener(uri -> {
                                    RentalData rentalData = new RentalData(rentalNumberArray[count], uri,
                                            (String) snapshot.get("productName"), (String) snapshot.get("location"), (String) snapshot.get("price"),
                                            (Boolean) snapshot.get("rental") );

                                    viewModel.rentalArrayList.add(rentalData);
                                    count++;
                                    doInBackground(null);
                                });
                    });
        }
        else
            publishProgress();

        return null;
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
        binding.rentalRecyclerview.getAdapter().notifyDataSetChanged();

    }
}
