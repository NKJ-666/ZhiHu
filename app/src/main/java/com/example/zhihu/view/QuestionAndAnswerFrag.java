package com.example.zhihu.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.zhihu.R;
import com.example.zhihu.bean.Question;
import com.example.zhihu.bean.User;
import com.example.zhihu.databinding.QuestionAndAnswerFragementBinding;
import com.example.zhihu.databinding.QuestionDetailFragmentBinding;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.example.zhihu.viewModel.QuestionAndAnswerVM;

public class QuestionAndAnswerFrag extends Fragment {
    private QuestionDetailFragmentBinding binding;
    private MyDataBaseHelper helper;
    private Question question;
    private User user;
    private QuestionAndAnswerVM viewModel;

    public QuestionAndAnswerFrag(MyDataBaseHelper helper, Question question, User user){
        this.helper = helper;
        this.question = question;
        this.user = user;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.question_detail_fragment, container, false);
        init();
        return binding.getRoot();
    }

    private void init(){
        viewModel = new QuestionAndAnswerVM(binding, this, helper, question, user);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.question_answer_detail_fragment, new QuestionAnswerItemFrag(helper, question, user))
                .commit();
        viewModel.init();
    }
}
