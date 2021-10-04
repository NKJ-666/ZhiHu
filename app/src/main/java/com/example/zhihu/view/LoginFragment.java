package com.example.zhihu.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.zhihu.R;
import com.example.zhihu.databinding.LoginFragementBinding;
import com.example.zhihu.helper.MyDataBaseHelper;

public class LoginFragment extends Fragment {
    private MyDataBaseHelper helper;
    private LoginFragementBinding binding;

    public LoginFragment(MyDataBaseHelper helper){
        this.helper = helper;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.login_fragement, container, false);
        init();
        return binding.getRoot();
    }

    private void init(){
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginDialog dialog = new LoginDialog(helper);
                dialog.show(getParentFragmentManager(), "login_dialog");
            }
        });
    }
}
