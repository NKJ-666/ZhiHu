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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.zhihu.R;
import com.example.zhihu.adapter.HomeItemAdapter;
import com.example.zhihu.bean.Question;
import com.example.zhihu.bean.User;
import com.example.zhihu.databinding.HomeRecyclerFragmentBinding;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.example.zhihu.viewModel.ProfileShareData;

import java.io.File;
import java.util.List;

public class HomeQuestionFrag extends Fragment {
    private Context context;

    private List<Question> questions;

    private MyDataBaseHelper helper;

    private User mUser;

    private HomeFragment fragment;

    private HomeRecyclerFragmentBinding binding;

    private HomeItemAdapter adapter;

    private ProfileShareData shareData;

    public HomeQuestionFrag(Context context, List<Question> questions, MyDataBaseHelper helper, User user, HomeFragment fragment){
        this.context = context;
        this.mUser = user;
        this.helper = helper;
        this.fragment = fragment;
        this.questions = questions;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_recycler_fragment, container, false);
        init();
        return binding.getRoot();
    }

    private void init(){
        adapter = new HomeItemAdapter(getContext(), questions, helper, fragment, mUser);
        binding.questionRecycler.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.questionRecycler.setLayoutManager(manager);
        shareData = new ViewModelProvider(requireActivity()).get(ProfileShareData.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        adapter.notifyDataSetChanged();
        shareData.getUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                mUser = user;
                if (user != null){
                    adapter = new HomeItemAdapter(getContext(), questions, helper, fragment, user);
                    binding.questionRecycler.setAdapter(adapter);
                }
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}
