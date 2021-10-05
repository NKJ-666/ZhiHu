package com.example.zhihu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zhihu.R;
import com.example.zhihu.bean.Answer;
import com.example.zhihu.bean.Comment;
import com.example.zhihu.databinding.AnswerItemBinding;
import com.example.zhihu.databinding.QuestionAndAnswerItemBinding;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.example.zhihu.util.DataTransformUtil;
import com.example.zhihu.viewModel.AnswerItemViewModel;

import java.io.File;
import java.util.List;

public class AnswerItemAdapter extends RecyclerView.Adapter<AnswerItemAdapter.ViewHolder>{
    private List<Answer> answers;

    private MyDataBaseHelper helper;

    private Context context;

    private AnswerItemViewModel viewModel;

    public AnswerItemAdapter(Context context, MyDataBaseHelper helper, List<Answer> answers){
        this.context = context;
        this.helper = helper;
        this.answers = answers;
        viewModel = new AnswerItemViewModel(helper);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AnswerItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.answer_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Answer answer = answers.get(position);
        holder.binding.setAnswer(answer);
        holder.binding.setAnswerUser(viewModel.getUserFromAnswer(answer));
        initMedia(holder.binding, answer);
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        AnswerItemBinding binding;

        public ViewHolder(AnswerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private void addImageView(String path, AnswerItemBinding binding){
        ImageView imageView = new ImageView(context);
        Glide.with(imageView.getContext()).load(new File(path)).into(imageView);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = 10;
        imageView.setLayoutParams(layoutParams);
        binding.addImageLinear.addView(imageView);
    }

    private void addVideo(String path, AnswerItemBinding binding){
        LinearLayout layout = new LinearLayout(context);
        VideoView videoView = new VideoView(context);
        videoView.setVideoPath(path);
        videoView.setMediaController(new MediaController(context));
        videoView.requestFocus();
        videoView.seekTo(10);
        videoView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 310));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 310);
        layoutParams.topMargin = 10;
        videoView.setLayoutParams(layoutParams);
        layout.addView(videoView);
        binding.addVideoLinear.addView(layout);
    }

    private void initMedia(AnswerItemBinding binding, Answer answer){
        binding.addImageLinear.removeAllViews();
        binding.addVideoLinear.removeAllViews();
        String[] imageUrls = DataTransformUtil.convertToArray(answer.getImageUrl());
        String[] videoUrls = DataTransformUtil.convertToArray(answer.getVideoUrl());
        if (imageUrls != null && imageUrls.length > 0){
            for (String imageUrl : imageUrls){
                addImageView(imageUrl, binding);
            }
        }
        if (videoUrls != null && videoUrls.length > 0){
            for (String videoUrl : videoUrls){
                addVideo(videoUrl, binding);
            }
        }
    }
}
