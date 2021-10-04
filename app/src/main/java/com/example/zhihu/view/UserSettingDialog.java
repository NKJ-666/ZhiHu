package com.example.zhihu.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.zhihu.R;
import com.example.zhihu.bean.User;
import com.example.zhihu.databinding.UserSettingDialogBinding;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.example.zhihu.util.GetRealUriUtil;
import com.example.zhihu.viewModel.ProfileViewModel;

import java.io.File;
import java.io.FileNotFoundException;

public class UserSettingDialog extends DialogFragment {
    private User user;
    private UserSettingDialogBinding binding;
    private ProfileViewModel viewModel;
    private static final int UPDATE_INFO = 1;
    private static final int GET_BACK_IMAGE = 2;

    public UserSettingDialog(User user, MyDataBaseHelper helper){
        this.user = user;
        viewModel = new ProfileViewModel(helper);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.user_setting_dialog, container, false);
        init();
        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case GET_BACK_IMAGE:{
                    String path = GetRealUriUtil.getImageRealPath(getContext(), data.getData());
                    Glide.with(binding.dialogHeadImage.getContext()).load(new File(path)).into(binding.dialogHeadImage);
                    user.setImageUrl(path);
                    break;
                }
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void chooseFromAlbum(){
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, GET_BACK_IMAGE);
    }

    private void init(){
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding.settingUsername.setText(user.getUsername());
        if (user.getImageUrl() == null){
            Glide.with(binding.dialogHeadImage.getContext()).load(R.drawable.test_backgroud).into(binding.dialogHeadImage);
        }
        binding.sexSpinner.setSelection(user.getSex());
        binding.sexSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user.setSex(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.dialogHeadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFromAlbum();
            }
        });
        binding.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setUsername(binding.settingUsername.getText().toString());
                viewModel.updateUser(user);
                Intent intent = new Intent();
                intent.putExtra("imageUrl", user.getImageUrl());
                intent.putExtra("username", user.getUsername());
                intent.putExtra("sex", user.getSex());
                getTargetFragment().onActivityResult(UPDATE_INFO, Activity.RESULT_OK, intent);
                dismiss();
            }
        });
    }
}
