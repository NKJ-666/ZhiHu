package com.example.zhihu.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.zhihu.R;
import com.example.zhihu.databinding.LoginDialogBinding;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.example.zhihu.viewModel.ProfileShareData;

public class LoginDialog extends DialogFragment {
    private MyDataBaseHelper helper;
    private LoginDialogBinding dialogBinding;
    private ProfileShareData shareData;

    public LoginDialog(MyDataBaseHelper helper){
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
        dialogBinding = DataBindingUtil.inflate(inflater, R.layout.login_dialog, container, false);
        init();
        return dialogBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shareData.getIsLogin().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    dismiss();
                }
            }
        });
    }

    private void init(){
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getChildFragmentManager().beginTransaction()
                .replace(R.id.dialog_login_fragment, new LoginDialogFragment(helper), null)
                .commit();
    }
}
