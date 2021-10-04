package com.example.zhihu.view;

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
import com.example.zhihu.databinding.RegisterDialogFragmentBinding;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.example.zhihu.viewModel.LoginDialogViewModel;
import com.example.zhihu.viewModel.ProfileShareData;

public class RegisterDialogFragment extends Fragment {
    private RegisterDialogFragmentBinding binding;
    private MyDataBaseHelper helper;
    private ProfileShareData shareData;
    private LoginDialogViewModel viewModel;

    public RegisterDialogFragment(MyDataBaseHelper helper){
        this.helper = helper;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.register_dialog_fragment, container, false);
        init();
        return binding.getRoot();
    }

    private void init(){
        shareData = new ViewModelProvider(requireActivity()).get(ProfileShareData.class);
        viewModel = new LoginDialogViewModel(helper, getContext(), binding, shareData);
        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel.register()){
                    shareData.setIsLogin(true);
                }
            }
        });
    }
}
