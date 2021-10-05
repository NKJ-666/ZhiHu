package com.example.zhihu.viewModel;

import com.example.zhihu.bean.Question;
import com.example.zhihu.bean.User;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.example.zhihu.model.UserModel;

public class QuestionItemViewModel {
    private UserModel userModel;

    private MyDataBaseHelper helper;

    public QuestionItemViewModel(MyDataBaseHelper helper){
        this.helper = helper;
        userModel = new UserModel(helper);
    }

    public User getUserFromQuestion(Question question){
        return userModel.queryUserFromId(question.getUid());
    }

}
