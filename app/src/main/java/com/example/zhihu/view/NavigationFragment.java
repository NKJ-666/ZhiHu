package com.example.zhihu.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.zhihu.R;
import com.example.zhihu.databinding.NavigationFragmentBinding;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationFragment extends Fragment {
    private NavigationFragmentBinding binding;
    private MyDataBaseHelper helper;

    public NavigationFragment(MyDataBaseHelper helper){
        this.helper = helper;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.navigation_fragment, container, false);
        init();
        return binding.getRoot();
    }

    private void init(){
        getChildFragmentManager().beginTransaction()
                .replace(R.id.main_fragment, new HomeFragment(helper), null)
                .commit();
        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:{
                        getChildFragmentManager().beginTransaction()
                                .replace(R.id.main_fragment, new HomeFragment(helper), null)
                                .commit();
                        break;
                    }
                    case R.id.profile:{
                        getChildFragmentManager().beginTransaction()
                                .replace(R.id.main_fragment, new ProfileFragment(helper), null)
                                .commit();
                        break;
                    }
                    default:
                        break;
                }
                return true;
            }
        });
    }
}
