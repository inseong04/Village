package com.example.village.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.village.R;
import com.example.village.databinding.FragmentHomeBinding;
import com.example.village.home.search.SearchActivity;
import com.example.village.productwritng.ProductWriting;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class Home extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    protected FirebaseFirestore db;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider((ViewModelStoreOwner) mContext).get(HomeViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        binding.setActivity(this);

        db = FirebaseFirestore.getInstance();
        db.collection("post")
                .document("information")
                .get()
                .addOnCompleteListener( task -> {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    try {
                        int postNumber = Integer.parseInt(String.valueOf(documentSnapshot.get("postNumbers")));
                        GetPostAsyncTask getPostAsyncTask = new GetPostAsyncTask(mContext, binding, postNumber);
                        getPostAsyncTask.execute();
                    } catch (NullPointerException e) {

                    }
                });


        /*String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();*/

  /*      viewModel.arrayListMutableLiveData.observe(getViewLifecycleOwner(), v -> {
            viewModel.setProduct();
        });

\

        Log.e("postNumber",String.valueOf(postNumbers));
        for(int i=1; i<=viewModel.getPostNumber(); i++) {
            Log.e("test","for : "+i);
            getPostInformation(i);

        }
        HomeAdapter adapter = new HomeAdapter(mContext);
        binding.homeRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        binding.homeRecyclerview.setAdapter(adapter);*/
        return binding.getRoot();
    }

/*    @Override
        public void onDestroyView() {
        super.onDestroyView();
        viewModel.product.setValue(new ArrayList<HomeData>());
    }*/

 /*   protected void getPostInformation(int num) {

        final String[] title = new String[1];
        final String[] location = new String[1];
        final String[] price = new String[1];
        final Uri[] postUri = new Uri[1];
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();

        db.collection("post")
                .document(String.valueOf(num))
                .get()
                .addOnCompleteListener(task -> {

                    DocumentSnapshot documentSnapshot = task.getResult();
                    Log.e("z", "postinforrun");
                    title[0] = String.valueOf(documentSnapshot.get("productName"));
                    Log.e("title", String.valueOf(documentSnapshot.get("productName")));
                    location[0] = String.valueOf(documentSnapshot.get("location"));
                    Log.e("location", String.valueOf(documentSnapshot.get("location")));
                    price[0] = String.valueOf(documentSnapshot.get("price"));
                    Log.e("price", String.valueOf(documentSnapshot.get("price")));

                    storageReference.child("postImg/" + "img" + "-" + num + "-0").getDownloadUrl().addOnSuccessListener(uri -> {
                            Log.e("sucees", "getpostimage");
                            postUri[0] = uri;
                            HomeData homeData = new HomeData(postUri[0], title[0], location[0], price[0]);
                            Log.e("homeData", homeData.toString());
                            viewModel.setArrayListMutableLiveData(homeData);
                    });
                });



    }*/

/*    protected void getPostImage(int num) {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child("postImg/"+"img"+"-"+num+"-0").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.e("sucees","getpostimage");
                postUri = uri;
            }
        });

    }*/

/*    protected void getPostNumber() {
        db = FirebaseFirestore.getInstance();
        db.collection("post")
                .document("information")
                .get()
                .addOnCompleteListener( task -> {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        try {
                            postNumbers = Integer.parseInt(String.valueOf(documentSnapshot.get("postNumbers")));
                            Log.e("getPost",String.valueOf(postNumbers));
                        } catch (NullPointerException e) {

                        }
                });
    }*/


    public void go_searchActivity(View view) {
        Intent intent = new Intent(getContext(), SearchActivity.class);
        startActivity(intent);
    }

    public void go_productWriting(View view) {
        Intent intent = new Intent(getContext(), ProductWriting.class);
        startActivity(intent);
    }




}