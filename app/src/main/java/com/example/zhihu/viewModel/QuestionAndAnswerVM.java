package com.example.zhihu.viewModel;

import android.view.View;
import android.widget.Toast;

import com.example.zhihu.R;
import com.example.zhihu.bean.Question;
import com.example.zhihu.bean.User;
import com.example.zhihu.databinding.QuestionDetailFragmentBinding;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.example.zhihu.view.HomeFragment;
import com.example.zhihu.view.NavigationFragment;
import com.example.zhihu.view.QuestionAndAnswerFrag;
import com.example.zhihu.view.QuestionAnswerItemFrag;
import com.example.zhihu.view.WriteAnswerFragment;

public class QuestionAndAnswerVM {
    private QuestionDetailFragmentBinding binding;
    private QuestionAndAnswerFrag fragment;
    private MyDataBaseHelper helper;
    private Question question;
    private User user;

    public QuestionAndAnswerVM(QuestionDetailFragmentBinding binding, QuestionAndAnswerFrag frag, MyDataBaseHelper helper, Question question, User user){
        this.binding = binding;
        this.fragment = frag;
        this.helper = helper;
        this.question = question;
        this.user = user;
    }

    public void init(){
        binding.detailBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.home_main_fragment, new NavigationFragment(helper))
                        .commit();
            }
        });
        binding.writeAnswerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null)
                    fragment.requireActivity().getSupportFragmentManager()
                             .beginTransaction()
                             .replace(R.id.home_main_fragment, new WriteAnswerFragment(helper, question, user))
                             .commit();
                else {
                    Toast.makeText(v.getContext(), "需要先登录才能写回答", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
