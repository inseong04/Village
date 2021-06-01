package com.example.village.productwritng;

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
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.village.MainActivity;
import com.example.village.R;
import com.example.village.databinding.ActivityProductWritingBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProductWriting extends AppCompatActivity {

    private ActivityProductWritingBinding binding;
    private ProductViewModel viewModel;
    private ProductAdapter adapter;
    FirebaseFirestore db;
    private final int PICK_IMAGE = 1;
    private Boolean uploadSuccess;
    int hashTagCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_writing);
        binding.setActivity(this);

        viewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        String price;
        final String[] strAmount = {""};

        checkSelfPermission();
        setUi();

/*        binding.goHomeBtn.setOnClickListener(v -> {
            finish();
        });*/

/*        viewModel.hashtagEtvText.observe(this, text -> {
            Log.e("z","observe run12412");
            if(text.substring(text.length()-1).equals(" ")) {
                Log.e("z","observe run");
                viewModel.hashtagEtvText.setValue(text+"#");
            }
        });*/

        binding.priceEtv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString()) && !s.toString().equals(strAmount[0])) {
                    strAmount[0] = toComma(s.toString().replace(",", ""));
                    binding.priceEtv.setText(strAmount[0]);
                    Editable editable = binding.priceEtv.getText();
                    Selection.setSelection(editable, strAmount[0].length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

/*        binding.hashtagEtv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (count > 0 && String.valueOf(s.charAt(count-1)).equals(" ")) {
                    Log.e("xc"," serhnfxrhjsfewdc");
                    binding.hashtagEtv.setText(s.toString() + "#");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

        binding.periodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    protected void setUi() {
        String[] period = getResources().getStringArray(R.array.period);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_item, period);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        binding.periodSpinner.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            int length = permissions.length;
            for (int i = 0; i < length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("MainActivity", "권한 허용 : " + permissions[i]);
                }
            }
        }
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
                    Toast.makeText(getApplicationContext(), "최대 9개까지 업로드 가능합니다.",
                            Toast.LENGTH_LONG).show();
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

    public void uploadPost(View view) {


        db = FirebaseFirestore.getInstance();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        final String[] writtenPost = new String[1];
        Log.e("postNumber", String.valueOf(getPostNumber())+"+"+"1");
        int postNumber = getPostNumber() + 1;

        db.collection("users")
                .document(uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        try {
                            Log.e("test", ": : : " + documentSnapshot.get("writtenPost").toString());
                            writtenPost[0] = (String) documentSnapshot.get("writtenPost") + "-" + String.valueOf(postNumber);
                        } catch (NullPointerException e) {
                            writtenPost[0] = String.valueOf(postNumber);
                        }
                        db.collection("users") // 사용자가 쓴 게시물 번호 저장
                                .document(uid)
                                .update("writtenPost", writtenPost[0]);
                    }
                });

        Map<String, Object> post = new HashMap<>();
        String productName = binding.productNameEtv.getText().toString();
        String price = binding.priceEtv.getText().toString();
        String period = binding.periodSpinner.getSelectedItem().toString();
        String hashTag = binding.hashtagEtv.getText().toString();
        String descripton = binding.descriptionEtv.getText().toString();
        post.put("productName", productName);
        post.put("price", price);
        post.put("period", period);
        post.put("hashTag", hashTag);
        post.put("description", descripton);

        db.collection("post") // 서버의 포스트 Count 증가
                .document("information")
                .update("postNumbers", postNumber)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e("xzcv","server post count");
                    }
                });

        db.collection("post") // 포스트 생성
                .document(String.valueOf(postNumber))
                .set(post)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e("uploadPost", "postUpload Success");
                    }
                });

        for (int i = 0; i < adapter.getItemCount(); i++) {
            String filename = "img-" + postNumber + "-" + i;
            Uri img = viewModel.arrayList.get(i).uri;
            StorageReference postStorage = storageReference.child("postImg/" + filename);
            UploadTask uploadTask = postStorage.putFile(img);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    uploadSuccess = true;
                    Log.e("uploadPost", "Image upload Success");
                }
            });
        }

        finish();
    }

    public int getPostNumber() {
        int[] result = new int[1];
        db = FirebaseFirestore.getInstance();
        db.collection("post")
                .document("information")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            result[0] = Integer.parseInt(String.valueOf(documentSnapshot.get("postNumbers")));
                            Log.e("ff", "postNumber" + result[0]);
                        }
                    }
                });
        Log.e("result","result : "+result[0]);
        return result[0];
    }

    public void callGallery(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);
        Log.e("a", "79line");
    }


    protected String toComma(String str) {
        Double value = Double.parseDouble(str);
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(value);
    }
}