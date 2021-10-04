package com.example.zhihu.viewModel;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.zhihu.bean.Question;
import com.example.zhihu.databinding.AddQuestionFragmentBinding;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.example.zhihu.model.QuestionModel;

import java.io.File;

public class AddQuestionViewModel {
    private QuestionModel model;

    private MyDataBaseHelper helper;

    private Context context;

    private AddQuestionFragmentBinding binding;

    public AddQuestionViewModel(Context context, MyDataBaseHelper helper, AddQuestionFragmentBinding binding){
        this.context = context;
        this.helper = helper;
        this.binding = binding;
        model = new QuestionModel(helper);
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

    public void sendQuestion(Question question){
        model.insertQuestionFromInstance(question);
    }
}
