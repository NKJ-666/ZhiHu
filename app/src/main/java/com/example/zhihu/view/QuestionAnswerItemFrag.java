package com.example.zhihu.view;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.zhihu.R;
import com.example.zhihu.adapter.QuestionAndAnswerAdapter;
import com.example.zhihu.bean.Question;
import com.example.zhihu.bean.User;
import com.example.zhihu.databinding.QuestionAndAnswerFragementBinding;
import com.example.zhihu.databinding.QuestionAndAnswerItemBinding;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.example.zhihu.model.QuestionModel;
import com.example.zhihu.viewModel.QuestionDetailViewModel;

public class QuestionAnswerItemFrag extends Fragment {
    private QuestionAndAnswerFragementBinding binding;
    private MyDataBaseHelper helper;
    private Question question;
    private QuestionDetailViewModel detailViewModel;
    private User user;

    public QuestionAnswerItemFrag(MyDataBaseHelper helper, Question question, User user){
        this.user = user;
        this.helper = helper;
        this.question = question;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.question_and_answer_fragement, container, false);
        init();
        return binding.getRoot();
    }

    private void init(){
        detailViewModel = new QuestionDetailViewModel(getContext(), helper, binding, this);
        QuestionAndAnswerAdapter adapter = new QuestionAndAnswerAdapter(getContext(),
                detailViewModel.getAnswersFromQuestion(question), helper, user, question, binding, this);
        binding.questionAndAnswerRecycler.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.questionAndAnswerRecycler.setLayoutManager(manager);
        detailViewModel.init(question);
    }
}
