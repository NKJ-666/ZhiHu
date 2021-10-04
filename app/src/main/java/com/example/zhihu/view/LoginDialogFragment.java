package com.example.zhihu.view;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.zhihu.R;
import com.example.zhihu.databinding.LoginDialogFragmentBinding;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.example.zhihu.viewModel.LoginDialogViewModel;
import com.example.zhihu.viewModel.ProfileShareData;

public class LoginDialogFragment extends Fragment {
    private MyDataBaseHelper helper;
    private ProfileShareData shareData;
    private LoginDialogFragmentBinding binding;
    private LoginDialogViewModel viewModel;

    public LoginDialogFragment(MyDataBaseHelper helper){
        this.helper = helper;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shareData = new ViewModelProvider(requireActivity()).get(ProfileShareData.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.login_dialog_fragment, container, false);
        viewModel = new LoginDialogViewModel(helper, getContext(), binding, shareData);
        init();
        return binding.getRoot();
    }

    private void init(){
        binding.registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.dialog_login_fragment, new RegisterDialogFragment(helper), null)
                        .commit();
            }
        });
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel.login())
                    shareData.setIsLogin(true);
            }
        });
    }

}
