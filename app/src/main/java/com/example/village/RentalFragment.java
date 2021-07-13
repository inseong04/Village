package com.example.village;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.village.databinding.FragmentRentalBinding;
import com.example.village.screen.MainActivity;
import com.example.village.screen.home.HomeViewModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

public class RentalFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "DocSnippets";
    private HomeViewModel viewModel;
    private Context mContext;
    private int postNumber;
    private StorageReference storageReference;

    private FragmentRentalBinding binding;

    private View view;

    private int count = 0;

    public RentalFragment() {
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        String[] title;
        String[] location;
        String[] price;
        Uri[] postUri;

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rental, container, false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        binding.rentalRecyclerview.setLayoutManager(linearLayoutManager);
        if (viewModel.getProductArray().size() <= 0)
            Log.d(TAG, "onCreateView: ");

        db.collection("post")
                .document("information")
                .get()
                .addOnCompleteListener(task -> {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    try {
                        postNumber = Integer.parseInt(String.valueOf(documentSnapshot.get("postNumbers")));

                    } catch (NullPointerException e) {

                    }
                });

        title = new String[postNumber];
        location = new String[postNumber];
        price = new String[postNumber];
        postUri = new Uri[postNumber];

        for (int i = 1; i <= postNumber; i++) {
            int doc_i = i;
            db.collection("post")
                    .document(String.valueOf(doc_i))
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                int postnum = doc_i;
                                DocumentSnapshot documentSnapshot = task.getResult();

                                String name = String.valueOf(documentSnapshot.get("name"));
                                if(name == ((MainActivity)MainActivity.nContext).getProfileUsername()){

                                    title[doc_i - 1] = String.valueOf(documentSnapshot.get("productName"));
                                    location[doc_i - 1] = String.valueOf(documentSnapshot.get("location"));
                                    price[doc_i - 1] = String.valueOf(documentSnapshot.get("price"));

                                    storageReference.child("postImg/" + "img" + "-" + doc_i + "-0").getDownloadUrl().
                                            addOnSuccessListener(uri -> {
                                                postUri[doc_i - 1] = uri;
//                                              onComplete(task);
//                                              count++;
                                                RentalList RentalList = new RentalList(postUri[doc_i - 1], title[doc_i - 1], location[doc_i - 1], price[doc_i - 1]);
//                                              RentalAdapter adapter = new RentalAdapter(RentalList);
                                            });

                                }
                            }else{
                                Log.d(TAG, "fail");
                            }
                        }
                    });
        }

        return binding.getRoot();
    }
}