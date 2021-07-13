package com.example.village.screen.my.rentaledproduct;

import android.os.AsyncTask;
import android.util.Log;
import com.example.village.databinding.ActivityRentaledBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class GetRentaledAsyncTask extends AsyncTask {

    private final RentaledViewModel viewModel;
    private final ActivityRentaledBinding binding;
    private final String[] writtenPostArray;
    private StorageReference storageReference;
    private int count;

    GetRentaledAsyncTask(RentaledViewModel viewModel, ActivityRentaledBinding binding,
                       String[] writtenPostArray) {
        this.viewModel = viewModel;
        this.binding = binding;
        this.writtenPostArray = writtenPostArray;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        storageReference = FirebaseStorage.getInstance().getReference();
        count = 0;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        Log.e("count",String.valueOf(count)+"+"+writtenPostArray.length);
        if (count < writtenPostArray.length-1) {
            Log.e("test", "test");
            FirebaseFirestore.getInstance().collection("post")
                    .document(writtenPostArray[count])
                    .get()
                    .addOnCompleteListener(task -> {
                        DocumentSnapshot snapshot = task.getResult();
                        storageReference.child("postImg/img-" + writtenPostArray[count] + "-0").getDownloadUrl().
                                addOnSuccessListener(uri -> {
                                    RentaledData rentaledData = new RentaledData(writtenPostArray[count], uri,
                                            (String) snapshot.get("productName"), (String) snapshot.get("location"),
                                            (String) snapshot.get("price"));
                                    viewModel.rentaledArrayList.add(rentaledData);
                                    doInBackground(null);
                                    ++count;
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
        binding.rentaledRecyclerview.getAdapter().notifyDataSetChanged();

    }
}
