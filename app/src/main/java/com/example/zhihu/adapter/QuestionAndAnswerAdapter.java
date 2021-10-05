package com.example.zhihu.adapter;

import android.app.Activity;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zhihu.R;
import com.example.zhihu.bean.Answer;
import com.example.zhihu.bean.Comment;
import com.example.zhihu.bean.Question;
import com.example.zhihu.bean.User;
import com.example.zhihu.databinding.QuestionAndAnswerFragementBinding;
import com.example.zhihu.databinding.QuestionAndAnswerItemBinding;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.example.zhihu.util.DataTransformUtil;
import com.example.zhihu.view.QuestionAnswerItemFrag;
import com.example.zhihu.viewModel.QuestionDetailViewModel;

import java.io.File;
import java.util.List;

public class QuestionAndAnswerAdapter extends RecyclerView.Adapter<QuestionAndAnswerAdapter.ViewHolder> {
    private List<Answer> answers;
    private Context context;
    private QuestionDetailViewModel viewModel;
    private User user;
    private Question question;
    private MyDataBaseHelper helper;

    public QuestionAndAnswerAdapter(Context context, List<Answer> answers, MyDataBaseHelper helper, User user, Question question, QuestionAndAnswerFragementBinding binding, Fragment fragment){
        this.answers = answers;
        this.context = context;
        this.user = user;
        this.question = question;
        this.helper = helper;
        viewModel = new QuestionDetailViewModel(context, helper, binding, fragment);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        QuestionAndAnswerItemBinding binding;

        public ViewHolder(QuestionAndAnswerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public QuestionAndAnswerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QuestionAndAnswerItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.question_and_answer_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Answer answer = answers.get(position);
        QuestionAndAnswerItemBinding itemBinding = holder.binding;
        itemBinding.setOwnUser(user);
        itemBinding.setQuestion(question);
        itemBinding.setAnswer(answer);
        itemBinding.setAnswerUser(viewModel.getUserFromAnswer(answer));
        initMedia(answer, itemBinding);
        List<Comment> comments = viewModel.getCommentsFromAnswer(answer);
        CommentItemAdapter adapter = new CommentItemAdapter(context, helper, comments);
        init(itemBinding, answer);
        viewModel.init(itemBinding, user, answer);
        itemBinding.questionCommentDetailRecycler.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        itemBinding.questionCommentDetailRecycler.setLayoutManager(manager);
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    private void initMedia(Answer answer, QuestionAndAnswerItemBinding binding){
        binding.addImageLinear.removeAllViews();
        binding.addVideoLinear.removeAllViews();
        String [] imageUrls = DataTransformUtil.convertToArray(answer.getImageUrl());
        String [] videoUrls = DataTransformUtil.convertToArray(answer.getVideoUrl());
        if (imageUrls != null && imageUrls.length > 0){
            for (String image : imageUrls){
                addImageView(image, binding);
            }
        }
        if (videoUrls != null && videoUrls.length > 0){
            for (String video : videoUrls){
                addVideo(video, binding);
            }
        }
    }

    private void addImageView(String path, QuestionAndAnswerItemBinding binding){
        ImageView imageView = new ImageView(context);
        Glide.with(imageView.getContext()).load(new File(path)).into(imageView);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = 10;
        imageView.setLayoutParams(layoutParams);
        binding.addImageLinear.addView(imageView);
    }

    private void addVideo(String path, QuestionAndAnswerItemBinding binding){
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

    private void init(QuestionAndAnswerItemBinding binding, Answer answer){
        if (user == null){
            binding.sendCommentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "需要先登录才能评论", Toast.LENGTH_SHORT).show();
                }
            });
        }
        binding.sendCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentText = binding.commentEditText.getText().toString();
                if (commentText.isEmpty()){
                    Toast.makeText(context, "评论不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    Comment comment = new Comment();
                    comment.setUid(user.getUid());
                    comment.setAid(answer.getAid());
                    answer.setCommentCount(answer.getCommentCount() + 1);
                    viewModel.updateCommentCount(answer);
                    binding.commentCount.setText(String.valueOf(Integer.parseInt(binding.commentCount.getText().toString()) + 1));
                    comment.setCommentText(commentText);
                    viewModel.insertComment(comment);
                    answer.setCid(viewModel.getAllCommentIdFromAnswer(answer));
                    viewModel.updateAnswerCid(answer);
                    CommentItemAdapter adapter = new CommentItemAdapter(context, helper, viewModel.getCommentsFromAnswer(answer));
                    binding.questionCommentDetailRecycler.setAdapter(adapter);
                    binding.commentEditText.setText("");
                }
            }
        });
    }
}
