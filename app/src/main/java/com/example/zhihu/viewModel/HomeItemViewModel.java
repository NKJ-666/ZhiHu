package com.example.zhihu.viewModel;

import androidx.lifecycle.ViewModel;

import com.example.zhihu.model.AnswerModel;
import com.example.zhihu.model.QuestionModel;
import com.example.zhihu.model.UserModel;

public class HomeItemViewModel extends ViewModel {
    private UserModel userModel;
    private QuestionModel questionModel;
    private AnswerModel answerModel;

    public HomeItemViewModel(UserModel userModel, QuestionModel questionModel, AnswerModel answerModel){
        this.userModel = userModel;
        this.answerModel = answerModel;
        this.questionModel = questionModel;
    }


}
