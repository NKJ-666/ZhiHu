package com.example.zhihu.view;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.zhihu.R;
import com.example.zhihu.bean.User;
import com.example.zhihu.databinding.MyListFragmentBinding;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.example.zhihu.viewModel.QuestionViewModel;

public class MyListFragment extends Fragment {
    private MyListFragmentBinding binding;

    private MyDataBaseHelper helper;

    private User user;

    private QuestionViewModel viewModel;

    public MyListFragment(MyDataBaseHelper helper, User user){
        this.helper = helper;
        this.user = user;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.my_list_fragment, container, false);
        init();
        return binding.getRoot();
    }

    private void init(){
        viewModel = new QuestionViewModel(helper, getContext());
        if (user != null){
            viewModel.init(binding, user);
        }else {
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
}
