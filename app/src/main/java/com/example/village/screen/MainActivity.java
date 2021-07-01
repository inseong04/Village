package com.example.village.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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
import static com.example.village.util.NetworkStatus.TYPE_NOT_CONNECTED;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    My myFragment;
    ProfileFragment profileFragment;
    ChangeprofileFragment changeprofileFragment;
    LocationFragment locationFragment;
    ChangePhoneFragment changePhoneFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Context mContext = getApplicationContext();
        int networkStatus = NetworkStatus.getConnectivityStatus(mContext);
        Fragment Chat = new Chat();
        Fragment Home = new Home();
        Fragment My = new My();

        myFragment = new My();
        profileFragment = new ProfileFragment();
        changeprofileFragment = new ChangeprofileFragment();
        locationFragment = new LocationFragment();
        changePhoneFragment = new ChangePhoneFragment();

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
        }
    }

}