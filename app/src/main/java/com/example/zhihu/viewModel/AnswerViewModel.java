package com.example.zhihu.viewModel;

import android.content.Context;
import android.os.Handler;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.zhihu.adapter.AnswerItemAdapter;
import com.example.zhihu.bean.User;
import com.example.zhihu.databinding.MyApproveAnswerBinding;
import com.example.zhihu.databinding.MyCollectAnswerBinding;
import com.example.zhihu.databinding.MyFollowingAnswerBinding;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.example.zhihu.model.AnswerModel;

public class AnswerViewModel {
    private Context context;
    private MyDataBaseHelper helper;
    private AnswerModel answerModel;

    public AnswerViewModel(Context context, MyDataBaseHelper helper){
        this.context = context;
        this.helper = helper;
        answerModel = new AnswerModel(helper);
    }

    public void init(MyCollectAnswerBinding binding, User user){
        AnswerItemAdapter adapter = new AnswerItemAdapter(context, helper, answerModel.getAllCollectAnswer(user));
        binding.myCollectAnswer.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        binding.myCollectAnswer.setLayoutManager(manager);
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

    public void init(MyApproveAnswerBinding binding, User user){
        AnswerItemAdapter adapter = new AnswerItemAdapter(context, helper, answerModel.getAllApproveAnswer(user));
        binding.myApproveAnswerRecycler.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        binding.myApproveAnswerRecycler.setLayoutManager(manager);
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

    public void init(MyFollowingAnswerBinding binding, User user){
        AnswerItemAdapter adapter = new AnswerItemAdapter(context,helper, answerModel.getAllFollowingAnswer(user));
        binding.myFollowingRecyclerAnswer.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        binding.myFollowingRecyclerAnswer.setLayoutManager(manager);
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
