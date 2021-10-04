package com.example.zhihu.viewModel;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.zhihu.R;
import com.example.zhihu.bean.Answer;
import com.example.zhihu.bean.Question;
import com.example.zhihu.bean.User;
import com.example.zhihu.databinding.WriteAnswerFragmentBinding;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.example.zhihu.model.AnswerModel;
import com.example.zhihu.model.QuestionModel;
import com.example.zhihu.model.UserModel;
import com.example.zhihu.view.QuestionAndAnswerFrag;
import com.example.zhihu.view.WriteAnswerFragment;

import java.io.File;
import java.util.List;

public class WriteAnswerViewModel {
    private MyDataBaseHelper helper;

    private Context context;

    private WriteAnswerFragmentBinding binding;

    private WriteAnswerFragment fragment;

    private User user;

    private Question question;
    private AnswerModel answerModel;
    private QuestionModel questionModel;
    private static final int GET_BACK_IMAGE = 2;
    private static final int GET_BACK_VIDEO = 1;

    public WriteAnswerViewModel(Context context, MyDataBaseHelper helper, WriteAnswerFragmentBinding binding, WriteAnswerFragment fragment, Question question, User user){
        this.context = context;
        this.helper = helper;
        this.binding = binding;
        this.fragment = fragment;
        this.question = question;
        this.user = user;
        answerModel = new AnswerModel(helper);
        questionModel = new QuestionModel(helper);
    }

    public void init(){
        binding.backFromWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.home_main_fragment, new QuestionAndAnswerFrag(helper, question, user))
                        .commit();
            }
        });
        binding.addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFromAlbum();
            }
        });
        binding.addVideoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseVideoFromAlbum();
            }
        });
    }

    private void chooseFromAlbum(){
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        fragment.startActivityForResult(intent, GET_BACK_IMAGE);
    }

    private void chooseVideoFromAlbum(){
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "video/*");
        fragment.startActivityForResult(intent, GET_BACK_VIDEO);
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

    public void addVideoView(String path){
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

    public void insertAnswer(Answer answer){
        answerModel.insertAnswerFromInstance(answer);
    }

    public List<String> getAllAnswerIdFromQuestion(Question question){
        return answerModel.getAllAnswerIdFromQuestion(question);
    }

    public void updateQuestionAid(Question question){
        questionModel.updateQuestionAid(question);
    }
}
