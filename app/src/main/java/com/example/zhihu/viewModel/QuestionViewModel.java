package com.example.zhihu.viewModel;

import android.content.Context;
import android.os.Handler;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.zhihu.adapter.QuestionItemAdapter;
import com.example.zhihu.bean.User;
import com.example.zhihu.databinding.MyFollowingQuestionBinding;
import com.example.zhihu.databinding.MyListFragmentBinding;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.example.zhihu.model.QuestionModel;

public class QuestionViewModel {
    private MyDataBaseHelper helper;
    private QuestionModel questionModel;
    private Context context;

    public QuestionViewModel(MyDataBaseHelper helper, Context context){
        this.helper = helper;
        questionModel = new QuestionModel(helper);
        this.context = context;
    }

    public void init(MyListFragmentBinding binding, User user){
        QuestionItemAdapter adapter = new QuestionItemAdapter(context, helper, questionModel.getAllUserQuestion(user));
        binding.myQuestionRecycler.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        binding.myQuestionRecycler.setLayoutManager(manager);
        binding.myQuestionRecycler.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    public void init(MyFollowingQuestionBinding binding, User user){
        QuestionItemAdapter adapter = new QuestionItemAdapter(context, helper, questionModel.getAllUserQuestion(user));
        binding.myFollowingRecyclerQuestion.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        binding.myFollowingRecyclerQuestion.setLayoutManager(manager);
        binding.myFollowingRecyclerQuestion.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }
}
