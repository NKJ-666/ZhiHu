package com.example.zhihu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zhihu.R;
import com.example.zhihu.bean.Comment;
import com.example.zhihu.databinding.CommentItemBinding;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.example.zhihu.viewModel.CommentItemViewModel;

import java.util.List;

public class CommentItemAdapter extends RecyclerView.Adapter<CommentItemAdapter.ViewHolder> {
    private Context context;
    private List<Comment> comments;
    private MyDataBaseHelper helper;
    private CommentItemViewModel viewModel;

    public CommentItemAdapter(Context context, MyDataBaseHelper helper, List<Comment> comments){
        this.context = context;
        this.helper = helper;
        this.comments = comments;
        viewModel = new CommentItemViewModel(context, helper);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CommentItemBinding binding;

        public ViewHolder(CommentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public CommentItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CommentItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.comment_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.binding.setComment(comment);
        holder.binding.setCommentUser(viewModel.getUserFromComment(comment));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}
