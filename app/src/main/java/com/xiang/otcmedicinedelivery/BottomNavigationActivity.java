package com.xiang.otcmedicinedelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class BottomNavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        BottomNavigationView navigationView = findViewById(R.id.chef_bottom_navigation);

        // https://www.youtube.com/watch?v=rknGiw9Vcno
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.Home:
                        fragment=new HomeFragment();
                        break;
                    case R.id.Cart:
                        fragment=new CartFragment();
                        break;
                    case R.id.Order:
                        fragment=new OrderFragment();
                        break;
                    case R.id.Profile:
                        fragment=new ProfileFragment();
                        break;
                }
                return loadfragment(fragment);
            }
        });
    }

    private boolean loadfragment(Fragment fragment) {

        if (fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,fragment).commit();
            return true;
        }
        return false;
    }
}

