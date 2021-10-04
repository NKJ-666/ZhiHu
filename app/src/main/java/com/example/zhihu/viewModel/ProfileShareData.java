package com.example.zhihu.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.zhihu.bean.User;

public class ProfileShareData extends ViewModel {
    private MutableLiveData<Boolean> isLogin = new MutableLiveData<>();

    private MutableLiveData<User> user = new MutableLiveData<>();

    public MutableLiveData<User> getUser() {
        return user;
    }

    public void setUser(User user){
        this.user.setValue(user);
    }

    public MutableLiveData<Boolean> getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin.setValue(isLogin);
    }
}
