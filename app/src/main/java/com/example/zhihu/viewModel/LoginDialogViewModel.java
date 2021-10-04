package com.example.zhihu.viewModel;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.zhihu.bean.User;
import com.example.zhihu.databinding.LoginDialogFragmentBinding;
import com.example.zhihu.databinding.ProfileFragmentBinding;
import com.example.zhihu.databinding.RegisterDialogFragmentBinding;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.example.zhihu.model.UserModel;

public class LoginDialogViewModel extends ViewModel {
    private Context context;
    private UserModel userModel;
    private LoginDialogFragmentBinding binding;
    private RegisterDialogFragmentBinding binding1;
    private ProfileShareData shareData;

    public LoginDialogViewModel(MyDataBaseHelper helper, Context context, LoginDialogFragmentBinding binding, ProfileShareData shareData){
        userModel = new UserModel(helper);
        this.context = context;
        this.binding = binding;
        this.shareData = shareData;
    }

    public LoginDialogViewModel(MyDataBaseHelper helper, Context context, RegisterDialogFragmentBinding binding1, ProfileShareData shareData){
        userModel = new UserModel(helper);
        this.binding1 = binding1;
        this.context = context;
        this.shareData = shareData;
    }

    public void insertUser(String accountNumber, String password){
        userModel.insertUser(accountNumber, password);
    }

    public void updateUserFromInstance(User user){
        userModel.updateUserFromInstance(user);
    }

    public User getUserFromId(int uid){
        return userModel.queryUserFromId(uid);
    }

    public User queryUserFromAccount(String userAccount){
        return userModel.queryUserFromAccount(userAccount);
    }

    public boolean login(){
        String accountNumber = binding.accountEdit.getText().toString();
        String password = binding.passwordEdit.getText().toString();
        if (accountNumber.length() < 8 || accountNumber.length() > 11){
            Toast.makeText(context, "请输入指定位数的账号", Toast.LENGTH_SHORT).show();
        }else {
            User user = userModel.queryUserFromAccount(accountNumber);
            if (user == null){
                Toast.makeText(context, "账户不存在", Toast.LENGTH_SHORT).show();
            }else{
                if (!password.equals(user.getPassword())){
                    Toast.makeText(context, "密码错误", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "登陆成功", Toast.LENGTH_SHORT).show();
                    shareData.setUser(user);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean register(){
        String accountNumber = binding1.accountEdit.getText().toString();
        String password = binding1.passwordEdit.getText().toString();
        if(accountNumber.length() < 8 || accountNumber.length() > 11){
            Toast.makeText(context, "请输入指定位数的账号", Toast.LENGTH_SHORT).show();
        }else if(password.length() < 6){
            Toast.makeText(context, "密码至少为6位", Toast.LENGTH_SHORT).show();
        }else {
            User user = queryUserFromAccount(accountNumber);
            if (user == null){
                insertUser(accountNumber, password);
                shareData.setUser(queryUserFromAccount(accountNumber));
                Toast.makeText(context, "注册成功", Toast.LENGTH_SHORT).show();
                return true;
            }else {
                Toast.makeText(context, "账户已存在", Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }
}
