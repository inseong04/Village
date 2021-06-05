package com.example.village.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.village.R;
import com.example.village.databinding.FragmentHomeBinding;
import com.example.village.home.search.SearchActivity;
import com.example.village.productwritng.ProductWriting;
import com.example.village.rdatabase.UserDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class Home extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    protected FirebaseFirestore db;
    protected int postNumbers = 1;
    protected String title;
    protected String location;
    protected String price;
    protected Uri postUri;
    private Context mContext;
    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        binding.setActivity(this);

        db = FirebaseFirestore.getInstance();
        viewModel = new ViewModelProvider((ViewModelStoreOwner) mContext).get(HomeViewModel.class);
        String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        getPostNumbers();
        Log.e("postNumber",String.valueOf(postNumbers));
        for(int i=1; i<=postNumbers; i++) {
            getPostInformation(i);
            getPostImage(i);
            Log.e("v",postUri+", "+title+", "+location+", "+price);
            HomeData homeData = new HomeData(postUri, title, location, price);
            viewModel.arrayList.add(homeData);
            Log.e("a","aa"+viewModel.arrayList.get(i-1).toString());
        }

        HomeAdapter adapter = new HomeAdapter(mContext);
        binding.homeRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        binding.homeRecyclerview.setAdapter(adapter);
        return binding.getRoot();
    }

    protected void getPostInformation(int num) {
        db.collection("post")
                .document(String.valueOf(num))
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        Log.e("z","postinforrun");
                        title = String.valueOf(documentSnapshot.get("productName"));
                        location = String.valueOf(documentSnapshot.get("location"));
                        price = String.valueOf(documentSnapshot.get("price"));
                    }
                });
    }

    protected void getPostImage(int num) {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child("postImg/"+"img"+"-"+num+"-0").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.e("sucees","getpostimage");

                postUri = uri;
            }
        });

    }

    protected void getPostNumbers() {
        db.collection("post")
                .document("information")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        try {
                            postNumbers = Integer.parseInt(String.valueOf(documentSnapshot.get("postNumbers")));
                        } catch (NullPointerException e) {

                        }
                    }
                });
    }


    public void go_searchActivity(View view) {
        Intent intent = new Intent(getContext(), SearchActivity.class);
        startActivity(intent);
    }

    public void go_productWriting(View view) {
        Intent intent = new Intent(getContext(), ProductWriting.class);
        startActivity(intent);
    }


}