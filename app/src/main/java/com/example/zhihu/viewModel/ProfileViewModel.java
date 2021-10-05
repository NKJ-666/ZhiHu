package com.example.zhihu.viewModel;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.example.zhihu.bean.User;
import com.example.zhihu.databinding.ProfileFragmentBinding;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.example.zhihu.model.UserModel;

public class ProfileViewModel {
    private UserModel model;
    private Context context;
    private AlertDialog dialog;

    public ProfileViewModel(MyDataBaseHelper helper, Context context){
        model = new UserModel(helper);
        this.context = context;
    }

    public void updateUser(User user){
        model.updateUserFromInstance(user);
    }

    public void initUnLogin(ProfileFragmentBinding binding, ProfileShareData shareData, User user){
        if (user != null){
            binding.unLoginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("是否退出登录");
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            shareData.setUser(null);
                            shareData.setIsLogin(false);
                        }
                    });
                    dialog = builder.create();
                    if (dialog != null){
                        dialog.show();
                    }
                }
            });
        }
    }
}
