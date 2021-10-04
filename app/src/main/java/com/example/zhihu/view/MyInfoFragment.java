package com.example.zhihu.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.zhihu.R;
import com.example.zhihu.bean.User;
import com.example.zhihu.databinding.MyInfoFragmentBinding;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.example.zhihu.util.GetRealUriUtil;
import com.example.zhihu.viewModel.ProfileShareData;

import java.io.File;

public class MyInfoFragment extends Fragment {
    private MyInfoFragmentBinding binding;
    private MyDataBaseHelper helper;
    private ProfileShareData shareData;
    private User user;
    private Fragment fragment;
    private static final int UPDATE_INFO = 1;

    public MyInfoFragment(MyDataBaseHelper helper, User user){
        this.helper = helper;
        this.user = user;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.my_info_fragment, container, false);
        fragment = this;
        init();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shareData = new ViewModelProvider(requireActivity()).get(ProfileShareData.class);
        shareData.getUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                binding.setUser(user);
                if (user.getImageUrl() == null){
                    Glide.with(binding.myCircleImage.getContext()).load(R.drawable.test_backgroud).into(binding.myCircleImage);
                } else {
                    Glide.with(binding.myCircleImage.getContext()).load(new File(user.getImageUrl())).into(binding.myCircleImage);
                }
                if (user.getSex() == 0){
                    binding.sexImage.setImageResource(R.drawable.male);
                }else {
                    binding.sexImage.setImageResource(R.drawable.female);
                }
            }
        });
    }

    private void init(){
        binding.myCircleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSettingDialog dialog = new UserSettingDialog(user, helper);
                dialog.setTargetFragment(fragment, UPDATE_INFO);
                dialog.show(getParentFragmentManager(), "user_setting_dialog");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == UPDATE_INFO){
            if (resultCode == Activity.RESULT_OK){
                if (user.getImageUrl() != data.getStringExtra("imageUrl"))
                    Glide.with(binding.myCircleImage.getContext()).load(new File(data.getStringExtra("imageUrl"))).into(binding.myCircleImage);
                user.setUsername(data.getStringExtra("username"));
                binding.setUser(user);
                if (data.getIntExtra("sex", 3) == 0){
                    binding.sexImage.setImageResource(R.drawable.male);
                }else {
                    binding.sexImage.setImageResource(R.drawable.female);
                }
            }
        }
    }
}
