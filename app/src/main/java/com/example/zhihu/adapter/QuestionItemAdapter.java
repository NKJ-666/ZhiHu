package com.example.zhihu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zhihu.R;
import com.example.zhihu.bean.Question;
import com.example.zhihu.databinding.QuestionItemBinding;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.example.zhihu.viewModel.QuestionItemViewModel;

import java.util.List;

public class QuestionItemAdapter extends RecyclerView.Adapter<QuestionItemAdapter.ViewHolder> {
    private List<Question> questions;

    private MyDataBaseHelper helper;

    private Context context;

    private QuestionItemViewModel viewModel;

    public QuestionItemAdapter(Context context, MyDataBaseHelper helper, List<Question> questions){
        this.context = context;
        this.helper = helper;
        this.questions = questions;
        viewModel = new QuestionItemViewModel(helper);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        QuestionItemBinding binding;

        public ViewHolder(QuestionItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public QuestionItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QuestionItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.question_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionItemAdapter.ViewHolder holder, int position) {
        holder.binding.setQuestion(questions.get(position));
        holder.binding.setUser(viewModel.getUserFromQuestion(questions.get(position)));
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}
