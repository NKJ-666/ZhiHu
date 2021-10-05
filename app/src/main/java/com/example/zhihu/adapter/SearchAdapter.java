package com.example.zhihu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zhihu.R;
import com.example.zhihu.bean.Question;
import com.example.zhihu.bean.User;
import com.example.zhihu.databinding.SearchQuestionItemBinding;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.example.zhihu.model.QuestionModel;
import com.example.zhihu.view.HomeFragment;
import com.example.zhihu.view.QuestionAndAnswerFrag;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private List<Question> questionTexts;

    private Context context;

    private Fragment fragment;

    private MyDataBaseHelper helper;

    private User user;

    private QuestionModel model;

    public SearchAdapter(Context context, List<Question> questionTexts, Fragment fragment, MyDataBaseHelper helper, User user){
        this.context = context;
        this.questionTexts = questionTexts;
        this.fragment = fragment;
        this.helper = helper;
        this.user = user;
        model = new QuestionModel(helper);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        SearchQuestionItemBinding binding;

        public ViewHolder(SearchQuestionItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SearchQuestionItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.search_question_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Question question = questionTexts.get(position);
        holder.binding.setQuestion(question);
        holder.binding.questionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.home_main_fragment, new QuestionAndAnswerFrag(helper, model.getQuestionFromId(question.getQid()), user))
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionTexts.size();
    }
}
