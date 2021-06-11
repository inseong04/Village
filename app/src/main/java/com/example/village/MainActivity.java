package com.example.village;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.village.chat.Chat;
import com.example.village.databinding.ActivityMainBinding;
import com.example.village.home.Home;
import com.example.village.my.My;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.example.village.NetworkStatus.TYPE_NOT_CONNECTED;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Context mContext = getApplicationContext();
        int networkStatus = com.example.village.NetworkStatus.getConnectivityStatus(mContext);
        Fragment Chat = new Chat();
        Fragment Home = new Home();
        Fragment My = new My();

        if(networkStatus == TYPE_NOT_CONNECTED) {
            Toast.makeText(mContext, "빌리지를 이용하려면 Wifi연결이 필요합니다.", Toast.LENGTH_SHORT).show();
        }



        getSupportFragmentManager().beginTransaction().replace(R.id.layout_main_frame, Home).commitAllowingStateLoss();
        binding.bottomBar.setSelectedItemId(R.id.home);

        // 권한요청 구현 필요.
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
}