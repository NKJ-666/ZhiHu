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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.zhihu.R;
import com.example.zhihu.adapter.SearchAdapter;
import com.example.zhihu.bean.Question;
import com.example.zhihu.bean.User;
import com.example.zhihu.databinding.HomeFragmentBinding;
import com.example.zhihu.databinding.SearchListFragmentBinding;
import com.example.zhihu.helper.MyDataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class SearchListFragment extends Fragment {
    private SearchListFragmentBinding binding;

    private List<Question> questionText;

    private MyDataBaseHelper helper;

    private User user;

    private HomeFragmentBinding homeFragmentBinding;

    private Fragment fragment;

    public SearchListFragment(MyDataBaseHelper helper, List<Question> questionText, User user, HomeFragmentBinding binding){
        this.helper = helper;
        this.questionText = questionText;
        this.user = user;
        this.homeFragmentBinding = binding;
        this.fragment = this;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.search_list_fragment, container, false);
        init();
        return binding.getRoot();
    }

    private void init(){
        homeFragmentBinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Question> questions = new ArrayList<>();
                if (questionText.size() > 0){
                    for (Question question : questionText){
                        if (question.getQuestionText().contains(newText)){
                            questions.add(question);
                        }
                    }
                }
                SearchAdapter adapter = new SearchAdapter(getContext(), questions, fragment,  helper, user);
                binding.searchList.setAdapter(adapter);
                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                binding.searchList.setLayoutManager(manager);
                return true;
            }
        });
        homeFragmentBinding.searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.home_main_fragment, new HomeFragment(helper))
                        .commit();
                return true;
            }
        });
    }
}
