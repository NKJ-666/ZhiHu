package com.example.zhihu.viewModel;

import com.example.zhihu.bean.Answer;
import com.example.zhihu.bean.User;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.example.zhihu.model.UserModel;

public class AnswerItemViewModel {
    private MyDataBaseHelper helper;

    private UserModel userModel;

    public AnswerItemViewModel(MyDataBaseHelper helper){
        this.helper = helper;
        userModel = new UserModel(helper);
    }

    public User getUserFromAnswer(Answer answer){
        return userModel.queryUserFromId(answer.getUid());
    }
}
