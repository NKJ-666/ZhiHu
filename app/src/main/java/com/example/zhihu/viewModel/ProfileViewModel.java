package com.example.zhihu.viewModel;

import com.example.zhihu.bean.User;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.example.zhihu.model.UserModel;

public class ProfileViewModel {
    private UserModel model;

    public ProfileViewModel(MyDataBaseHelper helper){
        model = new UserModel(helper);
    }

    public void updateUser(User user){
        model.updateUserFromInstance(user);
    }
}
