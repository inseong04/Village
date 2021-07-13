package com.example.village.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.village.RentalFragment;
import com.example.village.screen.chat.Chat;
import com.example.village.screen.my.ChangePhoneFragment;
import com.example.village.screen.my.ChangeprofileFragment;
import com.example.village.screen.my.LocationFragment;
import com.example.village.screen.my.ProfileFragment;
import com.example.village.R;
import com.example.village.databinding.ActivityMainBinding;
import com.example.village.screen.home.Home;
import com.example.village.screen.my.My;
import com.example.village.util.NetworkStatus;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.village.util.NetworkStatus.TYPE_NOT_CONNECTED;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    My myFragment;
    ProfileFragment profileFragment;
    ChangeprofileFragment changeprofileFragment;
    LocationFragment locationFragment;
    ChangePhoneFragment changePhoneFragment;
    RentalFragment rentalFragment;

    public static Context nContext;

    private String profileusername;

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.e("te", "tes");
        Context mContext = getApplicationContext();
        int networkStatus = NetworkStatus.getConnectivityStatus(mContext);
        Fragment Chat = new Chat();
        Fragment Home = new Home();
        Fragment My = new My();
        nContext = this;

        myFragment = new My();
        profileFragment = new ProfileFragment();
        changeprofileFragment = new ChangeprofileFragment();
        locationFragment = new LocationFragment();
        changePhoneFragment = new ChangePhoneFragment();
        rentalFragment = new RentalFragment();

        db.collection("users").document(user.getUid()).get()
                .addOnCompleteListener(it -> {
                    profileusername = it.getResult().get("name").toString();
                    Log.v(TAG, profileusername);
                });

        if (networkStatus == TYPE_NOT_CONNECTED) {
            Toast.makeText(mContext, "빌리지를 이용하시려면 Wifi연결이 필요합니다.", Toast.LENGTH_SHORT).show();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.layout_main_frame, Home).commitAllowingStateLoss();
        binding.bottomBar.setSelectedItemId(R.id.home);

        binding.bottomBar.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.chat:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.layout_main_frame, Chat).commit();
                    return true;

                case R.id.home:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.layout_main_frame, Home).commit();
                    return true;

                case R.id.my:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.layout_main_frame, My).commit();
                    return true;

                default:
                    return false;

            }
        });

    }

    public void onFragmentChange(int index) {
        if (index == 0) {
            getSupportFragmentManager().beginTransaction().replace(R.id.layout_main_frame, myFragment).commit();
        } else if (index == 1) {
            getSupportFragmentManager().beginTransaction().replace(R.id.layout_main_frame, profileFragment).commit();
        } else if (index == 2) {
            getSupportFragmentManager().beginTransaction().replace(R.id.layout_main_frame, changeprofileFragment).commit();
        } else if (index == 3) {
            getSupportFragmentManager().beginTransaction().replace(R.id.layout_main_frame, locationFragment).commit();
        } else if (index == 4) {
            getSupportFragmentManager().beginTransaction().replace(R.id.layout_main_frame, changePhoneFragment).commit();
        } else if (index == 5) {
            getSupportFragmentManager().beginTransaction().replace(R.id.layout_main_frame, rentalFragment).commit();
        }
    }

    public String getProfileUsername() {
        Log.v(TAG, profileusername);
        return profileusername;
    }

    public void changeName() {
//        db.collection("users").document(user.getUid()).get()
//                .addOnCompleteListener(it->{
//                    profileusername = it.getResult().get("name").toString();
//                    Log.v(TAG, profileusername);
//                });
        finish();
        startActivity(new Intent(MainActivity.this, MainActivity.class));
    }

//    public interface onKeyBackPressedListener {
//        void onBackKey();
//    }
//
//    private onKeyBackPressedListener mOnKeyBackPressedListener;
//
//    public void setOnKeyBackPressedListener(onKeyBackPressedListener listener) {
//        mOnKeyBackPressedListener = listener;
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (mOnKeyBackPressedListener != null) {
//            mOnKeyBackPressedListener.onBackKey();
//        } else {
//            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
//                //Toast.makeText(nContext, "종료하려면 한번 더 누르세요.", Toast.LENGTH_SHORT).show();
//            } else {
//                super.onBackPressed();
//            }
//        }
//    }


}