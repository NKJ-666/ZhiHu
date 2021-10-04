package com.example.zhihu.viewModel;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.zhihu.bean.Answer;
import com.example.zhihu.bean.Comment;
import com.example.zhihu.bean.Question;
import com.example.zhihu.bean.User;
import com.example.zhihu.databinding.QuestionAndAnswerFragementBinding;
import com.example.zhihu.databinding.QuestionAndAnswerItemBinding;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.example.zhihu.model.AnswerModel;
import com.example.zhihu.model.CommentModel;
import com.example.zhihu.model.QuestionModel;
import com.example.zhihu.model.UserModel;
import com.example.zhihu.util.DataTransformUtil;

import java.io.File;
import java.util.List;

public class QuestionDetailViewModel {
    private AnswerModel answerModel;
    private CommentModel commentModel;
    private QuestionModel questionModel;
    private UserModel userModel;
    private MyDataBaseHelper helper;
    private Context context;
    private QuestionAndAnswerFragementBinding binding;

    public QuestionDetailViewModel(Context context, MyDataBaseHelper helper, QuestionAndAnswerFragementBinding binding){
        this.context = context;
        this.helper = helper;
        this.binding = binding;
        answerModel = new AnswerModel(helper);
        commentModel = new CommentModel(helper);
        questionModel = new QuestionModel(helper);
        userModel = new UserModel(helper);
    }

    public List<Comment> getCommentsFromAnswer(Answer answer){
        return commentModel.getCommentFromAnswer(answer);
    }

    public List<Answer> getAnswersFromQuestion(Question question){
        return answerModel.getAnswersFromQuestion(question);
    }

    public void init(Question question){
        String [] imageUrls = DataTransformUtil.convertToArray(question.getImageUrl());
        String [] videoUrls = DataTransformUtil.convertToArray(question.getVideoUrl());
        if (imageUrls != null && imageUrls.length > 0){
            for (String image : imageUrls){
                addImageView(image);
            }
        }
        if (videoUrls != null && videoUrls.length > 0){
            for (String video : videoUrls){
                addVideo(video);
            }
        }
        binding.setQuestion(question);
    }

    public void addImageView(String path){
        ImageView imageView = new ImageView(context);
        Glide.with(imageView.getContext()).load(new File(path)).into(imageView);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = 10;
        imageView.setLayoutParams(layoutParams);
        binding.addImageLinear.addView(imageView);
    }

    public void addVideo(String path){
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

    public User getUserFromAnswer(Answer answer){
        return userModel.queryUserFromId(answer.getUid());
    }

    public void init(Answer answer){

    }

    public void insertComment(Comment comment){
        commentModel.insertCommentFromInstance(comment);
    }

    public String getAllCommentIdFromAnswer(Answer answer){
        return commentModel.getAllCommentIdFromAnswer(answer);
    }

    public void updateAnswerCid(Answer answer){
        answerModel.updateAnswerCid(answer);
    }

    public void init(QuestionAndAnswerItemBinding binding, User user, Answer answer){
        int praisedCount = answer.getPraisedCount();
        binding.approveBtn.setText(praisedCount + "赞成");
        binding.approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.approveBtn.setText((praisedCount + 1) + "赞成");
                answer.setPraisedCount(praisedCount + 1);
                answerModel.updateAnswerPraisedCount(answer);
                binding.approveBtn.setEnabled(false);
            }
        });
    }
}
