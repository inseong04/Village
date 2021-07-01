package com.example.village.screen.productwriting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.example.village.R;
import com.example.village.databinding.ActivityProductWritingBinding;
import com.example.village.util.WarningDialogFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProductWriting extends AppCompatActivity {

    private ActivityProductWritingBinding binding;
    private ProductViewModel viewModel;
    private ProductAdapter adapter;
    private ProductWritingHelper productWritingHelper;
    private FirebaseFirestore db;
    private final int PICK_IMAGE = 1;
    private Boolean uploadSuccess;
    int postNumber;
    String userToken;
    String name;
    String location;
    SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss.SS");
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_writing);
        productWritingHelper = new ProductWritingHelper(binding, this);
        binding.setActivity(this);

        viewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        final String[] strAmount = {""};

        checkSelfPermission();
        productWritingHelper.setUi();
        getPostNumber();
        getFcmToken();
        getUserNameAndLocation();

        binding.writingBtn.setOnClickListener(v -> {

            if (productWritingHelper.isNotNullValue()) {
                uploadPost();
            }
        });

        binding.priceEtv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString()) && !s.toString().equals(strAmount[0])) {
                    strAmount[0] = productWritingHelper.toComma(s.toString().replace(",", ""));
                    binding.priceEtv.setText(strAmount[0]);
                    Editable editable = binding.priceEtv.getText();
                    Selection.setSelection(editable, strAmount[0].length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void checkSelfPermission() {
        String temp = "";
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.READ_EXTERNAL_STORAGE + " ";
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.WRITE_EXTERNAL_STORAGE + " ";
        }
        if (!TextUtils.isEmpty(temp)) {
            ActivityCompat.requestPermissions(this, temp.trim().split(" "), 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        viewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        ProductData productData;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this
                , LinearLayoutManager.HORIZONTAL, false);
        if (requestCode == RESULT_OK && resultCode == RESULT_OK) {
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                if (count > 9) {
                    WarningDialogFragment warningDialogFragment = new WarningDialogFragment("상품등록","최대 9개까지 \n업로드 가능합니다.");
                    warningDialogFragment.show(getSupportFragmentManager(), "waringDialog");
                    return;
                }
                for (int i = 0; i < count; i++) {
                    productData = new ProductData(data.getClipData().getItemAt(i).getUri());
                    viewModel.arrayList.add(productData);
                }
            }
        } else {
            try {
                productData = new ProductData(data.getData());
                viewModel.arrayList.add(productData);
            } catch (RuntimeException ignored) {
            }
        }
        this.adapter = new ProductAdapter(this);
        binding.imageRecycleView.setLayoutManager(layoutManager);
        binding.imageRecycleView.setAdapter(adapter);

    }

    public void getUserNameAndLocation() {
        db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        db.collection("users")
                .document(uid)
                .get()
                .addOnCompleteListener(task -> {
                   DocumentSnapshot documentSnapshot = task.getResult();
                   name = String.valueOf(documentSnapshot.get("name"));
                   location = String.valueOf(documentSnapshot.get("location"));
                });
    }

    public void getPostNumber() {
        db = FirebaseFirestore.getInstance();
        db.collection("post")
                .document("information")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        postNumber = Integer.parseInt(String.valueOf(documentSnapshot.get("postNumbers"))) + 1;
                    }
                });
    }

    public void getFcmToken( ){
        db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        db.collection("users")
                .document(uid)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        userToken = String.valueOf(documentSnapshot.get("fcmToken"));
                    }
                });
    }

    public void uploadPost() {

        db = FirebaseFirestore.getInstance();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        final String[] writtenPost = new String[1];

        Map<String, Object> map = new HashMap<>();

        map.put("postNumbers",postNumber);
        map.put("post-"+postNumber,binding.productNameEtv.getText().toString());

        db.collection("post") // 서버의 포스트 Count 증가
                .document("information")
                .update(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    // issue : post - information에 post-2 : 제목이 들어가야함. 테스트 필요
                    }
                });

        db.collection("users")
                .document(uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if(task.isSuccessful()) {

                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.get("writtenPost") == null) {
                                writtenPost[0] = (String) documentSnapshot.get("writtenPost") + "-" + String.valueOf(postNumber);
                            } else {
                                writtenPost[0] = String.valueOf(postNumber);
                            }

                            db.collection("users") // 사용자가 쓴 게시물 번호 저장
                                    .document(uid)
                                    .update("writtenPost", writtenPost[0]);
                        } else {
                            WarningDialogFragment warningDialogFragment = new WarningDialogFragment("상품등록", "오류가 발생하였습니다\n 다시 시도해주세요.");
                            warningDialogFragment.show(getSupportFragmentManager(), "dialogFragment");
                            finish();
                        }
                    }
                });

        Map<String, Object> post = new HashMap<>();
        String productName = binding.productNameEtv.getText().toString();
        String price = binding.priceEtv.getText().toString();
        String period = binding.periodSpinner.getSelectedItem().toString();
        String hashTag = binding.hashtagEtv.getText().toString();
        String descripton = binding.descriptionEtv.getText().toString();
        int imageNumber = adapter.getItemCount();
        long productTime = System.currentTimeMillis();
        post.put("name", name);
        post.put("location", location);
        post.put("rental",false);
        post.put("productName", productName);
        post.put("price", price + "원");
        post.put("period", period);
        post.put("hashTag", hashTag);
        post.put("description", descripton);
        post.put("imageNumber", imageNumber);
        post.put("productTime", productTime);
        post.put("fcmToken", userToken);

        db.collection("post") // 포스트 생성
                .document(String.valueOf(postNumber))
                .set(post).addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        WarningDialogFragment warningDialogFragment = new WarningDialogFragment("상품등록", "상품등록 중 에러가 발생했습니다 \n 다시 시도하여주세요.");
                        warningDialogFragment.show(getSupportFragmentManager(), "dialogFragment");
                        finish();
                    }
        });

        for (int i = 0; i < imageNumber; i++) {
            String filename = "img-" + String.valueOf(postNumber) + "-" + i;
            Uri img = viewModel.arrayList.get(i).uri;
            StorageReference postStorage = storageReference.child("postImg/" + filename);
            UploadTask uploadTask = postStorage.putFile(img);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    uploadSuccess = true;
                }
            });
        }

        finish();
    }


    public void callGallery(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);
    }


}