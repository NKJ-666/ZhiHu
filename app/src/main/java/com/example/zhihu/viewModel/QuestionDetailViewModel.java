package com.example.zhihu.viewModel;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.zhihu.R;
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
    private UserModel userModel;
    private QuestionModel questionModel;
    private MyDataBaseHelper helper;
    private Context context;
    private QuestionAndAnswerFragementBinding binding;
    private InfoShareData shareData;

    public QuestionDetailViewModel(Context context, MyDataBaseHelper helper, QuestionAndAnswerFragementBinding binding, Fragment fragment){
        this.context = context;
        this.helper = helper;
        this.binding = binding;
        answerModel = new AnswerModel(helper);
        commentModel = new CommentModel(helper);
        questionModel = new QuestionModel(helper);
        userModel = new UserModel(helper);
        shareData = new ViewModelProvider(fragment.requireActivity()).get(InfoShareData.class);
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
        if (user == null){
            binding.approveBtn.setEnabled(false);
            binding.followTextBtn.setEnabled(false);
        }else {
            String [] ids = getApproveId(user);
            if (ids != null){
                for(String id : ids){
                    if (Integer.parseInt(id) == answer.getAid()){
                        binding.approveBtn.setEnabled(false);
                    }
                }
            }
        }
        binding.approveBtn.setText(praisedCount + "赞成");
        binding.approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User mUser = getUserFromAnswer(answer);
                binding.approveBtn.setText((praisedCount + 1) + "赞成");
                answer.setPraisedCount(praisedCount + 1);
                answerModel.updateAnswerPraisedCount(answer);
                StringBuilder builder = new StringBuilder();
                builder.append(getApproveAnswerId(user) == null ? "" : getApproveAnswerId(user));
                builder.append(",").append(answer.getAid());
                if (builder.charAt(0) == ','){
                    builder.deleteCharAt(0);
                }
                user.setApproveAnswerId(builder.toString());
                updateUserApproveId(user);
                mUser.setPraisedCount(mUser.getPraisedCount() + 1);
                userModel.updateApproved(mUser);
                binding.approveBtn.setEnabled(false);
                shareData.setApproveCount(getUserFromAnswer(answer).getPraisedCount());
            }
        });
        if (user != null){
            String [] ids = DataTransformUtil.convertToArray(user.getCollectAnswerId());
            if (ids != null && ids.length > 0){
                for (String id : ids){
                    if (Integer.parseInt(id) == answer.getAid()){
                        binding.collectAnswer.setImageResource(R.drawable.collect_selected);
                        binding.collectAnswer.setEnabled(false);
                    }
                }
            }
            String [] uids = DataTransformUtil.convertToArray(user.getLikeUid());
            if (uids != null && uids.length > 0){
                for (String id : ids){
                    if (Integer.parseInt(id) == getUserFromAnswer(answer).getUid()){
                        binding.followTextBtn.setEnabled(false);
                    }
                }
            }
            binding.collectAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.collectCount.setText(String.valueOf(Integer.parseInt(binding.collectCount.getText().toString()) + 1));
                    answer.setCollectCount(answer.getCollectCount() + 1);
                    answerModel.updateCollectCount(answer);
                    StringBuilder builder = new StringBuilder();
                    builder.append(userModel.getCollectId(user) == null ? "" : userModel.getCollectId(user));
                    builder.append(",").append(answer.getAid());
                    if (builder.charAt(0) == ','){
                        builder.deleteCharAt(0);
                    }
                    user.setCollectAnswerId(builder.toString());
                    userModel.updateCollectAnswer(user);
                    binding.collectAnswer.setEnabled(false);
                    binding.setAnswer(answer);
                }
            });
        }else{
            binding.collectAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "需要先登录才能收藏", Toast.LENGTH_SHORT).show();
                }
            });
        }
        binding.followTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "关注成功", Toast.LENGTH_SHORT).show();
                User user1 = getUserFromAnswer(answer);
                StringBuilder builder = new StringBuilder();
                builder.append(userModel.getLikeUid(user) == null ? "" : userModel.getLikeUid(user));
                builder.append(",").append(user1.getUid());
                if (builder.charAt(0) == ','){
                    builder.deleteCharAt(0);
                }
                user.setLikeUid(builder.toString());
                user.setFollowingCount(user.getFollowingCount() + 1);
                userModel.updateUserFollow(user);
                userModel.updateLikeUid(user);
                user1.setFollowerCount(user1.getFollowerCount() + 1);
                userModel.updateUserFollowed(user1);
                binding.followTextBtn.setEnabled(false);
                shareData.setFollowingCount(user.getFollowingCount());
                if (user.getUid() == user1.getUid()){
                    shareData.setFollowerCount(user1.getFollowerCount());
                }
            }
        });
    }

    public void updateUserApproveId(User user){
        userModel.updateAnswerId(user);
    }

    public String getApproveAnswerId(User user){
        return userModel.getStringAnswerId(user);
    }

    public String [] getApproveId(User user){
        return userModel.getAnswerId(user);
    }

    public void updateCommentCount(Answer answer){
        answerModel.updateCommentCount(answer);
    }



}
