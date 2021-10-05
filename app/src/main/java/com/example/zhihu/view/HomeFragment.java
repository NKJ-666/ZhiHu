package com.example.zhihu.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
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
import com.example.zhihu.databinding.HomeFragmentBinding;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.example.zhihu.model.QuestionModel;
import com.example.zhihu.model.UserModel;
import com.example.zhihu.viewModel.ProfileShareData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

public class HomeFragment extends Fragment {
    private HomeFragmentBinding binding;
    private MyDataBaseHelper helper;
    private HomeItemAdapter adapter;
    private List<Question> questions;
    private QuestionModel questionModel;
    private UserModel userModel;
    private ProfileShareData shareData;
    private User mUser;
    private HomeFragment fragment;
    private List<Question> questionTexts;

    public HomeFragment(MyDataBaseHelper helper){
        this.helper = helper;
        questionModel = new QuestionModel(helper);
        initQuestions();
        questionTexts = questionModel.getAllQuestionText();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userModel = new UserModel(helper);
        fragment = this;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false);
        init();
        return binding.getRoot();
    }

    private void init(){
        shareData = new ViewModelProvider(requireActivity()).get(ProfileShareData.class);
        SQLiteStudioService.instance().start(getContext());
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.home_recycler_fragment, new HomeQuestionFrag(getContext(), questions, helper, mUser, fragment))
                .commit();
        if (mUser == null){
            binding.addQuestionBtn.setEnabled(false);
        }
        binding.addQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.home_main_fragment, new AddQuestionFragment(helper, mUser))
                        .commit();
            }
        });
        binding.searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.home_recycler_fragment, new SearchListFragment(helper, questionTexts, mUser, binding))
                        .commit();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shareData.getUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                mUser = user;
                if (user != null){
                    binding.addQuestionBtn.setEnabled(true);
                    if (user.getImageUrl() != null){
                        Glide.with(binding.homeHeadImage.getContext()).load(new File(user.getImageUrl())).into(binding.homeHeadImage);
                    }
                }else {
                    binding.homeHeadImage.setImageResource(R.drawable.test_backgroud);
                }
            }
        });
    }

    private void initQuestions(){
        questions = questionModel.getAllQuestion();
    }
}
