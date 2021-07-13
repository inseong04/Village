package com.example.village.screen.my.rentaledproduct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.village.R;
import com.example.village.databinding.ActivityRentaledBinding;
import com.example.village.screen.my.rentalproduct.GetRentalAsyncTask;
import com.example.village.screen.my.rentalproduct.RentalAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class RentaledActivity extends AppCompatActivity {

    private ActivityRentaledBinding binding;
    private RentaledViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rentaled);
        viewModel = new ViewModelProvider(this).get(RentaledViewModel.class);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());

        binding.rentaledRecyclerview.setLayoutManager(linearLayoutManager);
        RentaledAdapter adapter = new RentaledAdapter(viewModel);
        binding.rentaledRecyclerview.setAdapter(adapter);

        if (viewModel.rentaledArrayList.size() <= 0) {
            getPost();
        }

    }

    private void getPost() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .get()
                .addOnCompleteListener(task -> {
                    DocumentSnapshot documentSnapshot = task.getResult();

                    try {
                        String[] writtenPostArray = ((String) documentSnapshot.get("rentalProduct")).split("-");

                        GetRentaledAsyncTask getRentaledAsyncTask = new GetRentaledAsyncTask(viewModel, binding, writtenPostArray);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            getRentaledAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else {
                            getRentaledAsyncTask.execute();
                        }
                    } catch (NullPointerException e) {
                    }

                });
    }
}