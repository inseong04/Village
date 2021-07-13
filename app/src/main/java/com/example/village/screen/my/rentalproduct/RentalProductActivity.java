package com.example.village.screen.my.rentalproduct;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.village.R;
import com.example.village.databinding.ActivityRentalProductBinding;
import com.example.village.screen.home.HomeAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class RentalProductActivity extends AppCompatActivity {

    private ActivityRentalProductBinding binding;
    private RentalProductViewModel viewModel;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rental_product);
        viewModel = new ViewModelProvider(this).get(RentalProductViewModel.class);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());

        binding.rentalRecyclerview.setLayoutManager(linearLayoutManager);
        RentalAdapter adapter = new RentalAdapter(viewModel, getApplicationContext());
        binding.rentalRecyclerview.setAdapter(adapter);

        if (viewModel.rentalArrayList.size() <= 0) {
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
                        String[] rentalNumberArray = ((String) documentSnapshot.get("writtenPost")).split("-");
                        GetRentalAsyncTask getRentalAsyncTask = new GetRentalAsyncTask(viewModel, binding, rentalNumberArray);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            getRentalAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else {
                            getRentalAsyncTask.execute();
                        }
                    } catch (NullPointerException e) {
                    }

                });
    }

}
