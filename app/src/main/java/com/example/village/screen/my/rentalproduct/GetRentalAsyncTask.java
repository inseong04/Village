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
    private String[] title;
    private String[] location;
    private String[] price;
    GetRentalAsyncTask(RentalProductViewModel viewModel, ActivityRentalProductBinding binding,
                        String[] rentalNumberArray) {
        this.viewModel = viewModel;
        this.binding = binding;
        this.rentalNumberArray = rentalNumberArray;
        this.title = new String[rentalNumberArray.length];
        this.location = new String[rentalNumberArray.length];
        this.price = new String[rentalNumberArray.length];
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        storageReference = FirebaseStorage.getInstance().getReference();
        count = 0;
    }
    @Override
    protected Object doInBackground(Object[] objects) {

        Log.e("count",String.valueOf(count)+"+"+rentalNumberArray.length);
        if (count < rentalNumberArray.length-1) {
            FirebaseFirestore.getInstance().collection("post")
                    .document(rentalNumberArray[count])
                    .get()
                    .addOnCompleteListener(task -> {
                        DocumentSnapshot snapshot = task.getResult();
                        title[count] = (String) snapshot.get("productName");
                        location[count] = (String) snapshot.get("location");
                        price[count] = (String) snapshot.get("price");

                        storageReference.child("postImg/img-" + rentalNumberArray[count] + "-0").getDownloadUrl().
                                addOnSuccessListener(uri -> {
                                    Log.e("t", count+":"+title[count]);
                                    RentalData rentalData = new RentalData(rentalNumberArray[count], uri,
                                            title[count], location[count] , price[count]
                                            );

                                    viewModel.rentalArrayList.add(rentalData);
                                    doInBackground(null);
                                    count++;
                                    Log.e("co", String.valueOf(count));
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
