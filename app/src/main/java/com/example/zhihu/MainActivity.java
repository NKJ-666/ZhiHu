package com.example.zhihu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.zhihu.databinding.ActivityMainBinding;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.example.zhihu.view.HomeFragment;
import com.example.zhihu.view.NavigationFragment;
import com.example.zhihu.view.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private MyDataBaseHelper helper;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        helper = new MyDataBaseHelper(this, "zhihu.db", null, 10);
        helper.getWritableDatabase();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.home_main_fragment, new NavigationFragment(helper))
                .commit();
    }
}