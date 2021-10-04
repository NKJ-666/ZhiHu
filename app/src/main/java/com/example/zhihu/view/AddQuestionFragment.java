package com.example.zhihu.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.DrawableContainer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.zhihu.R;
import com.example.zhihu.bean.Question;
import com.example.zhihu.bean.User;
import com.example.zhihu.databinding.AddQuestionFragmentBinding;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.example.zhihu.util.DataTransformUtil;
import com.example.zhihu.util.GetFirstImageUtil;
import com.example.zhihu.util.GetRealUriUtil;
import com.example.zhihu.viewModel.AddQuestionViewModel;
import com.google.android.material.badge.BadgeDrawable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddQuestionFragment extends Fragment {
    private AddQuestionFragmentBinding binding;
    private MyDataBaseHelper helper;
    private User user;
    private Question question = new Question();
    private List<String> imageUrls = new ArrayList<>();
    private List<String> videoUrls = new ArrayList<>();
    private AddQuestionViewModel viewModel;
    private static final int GET_BACK_IMAGE = 2;
    private static final int GET_BACK_VIDEO = 1;

    public AddQuestionFragment(MyDataBaseHelper helper, User user){
        this.helper = helper;
        this.user = user;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_question_fragment, container, false);
        init();
        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case GET_BACK_IMAGE:{
                    if (data != null){
                        String path = GetRealUriUtil.getImageRealPath(getContext(), data.getData());
                        imageUrls.add(path);
                        viewModel.addImageView(path);
                    }
                    break;
                }
                case GET_BACK_VIDEO:{
                    if (data != null){
                        String path = GetRealUriUtil.getImageRealPath(getContext(), data.getData());
                        viewModel.addVideo(path);
                        videoUrls.add(path);
                    }
                    break;
                }
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void init(){
        binding.closeAddFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.home_main_fragment, new NavigationFragment(helper))
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
        binding.sendQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendQuestion();
            }
        });
        viewModel = new AddQuestionViewModel(getContext(), helper, binding);
    }

    private void sendQuestion(){
        String questionText = binding.questionTextEdit.getText().toString();
        if (questionText.isEmpty()){
            Toast.makeText(getContext(), "问题为空", Toast.LENGTH_SHORT).show();
        }else {
            question.setUid(user.getUid());
            question.setQuestionText(questionText);
            question.setQuestionDetail(binding.questionDetailText.getText().toString());
            if (imageUrls.size() > 0)
                question.setImageUrl(DataTransformUtil.convertToString(imageUrls.toArray(new String[imageUrls.size()])));
            if (videoUrls.size() > 0){
                question.setVideoUrl(DataTransformUtil.convertToString(videoUrls.toArray(new String[videoUrls.size()])));
            }
            viewModel.sendQuestion(question);
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.home_main_fragment, new NavigationFragment(helper))
                    .commit();
        }
    }

    private void chooseFromAlbum(){
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, GET_BACK_IMAGE);
    }

    private void chooseVideoFromAlbum(){
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "video/*");
        startActivityForResult(intent, GET_BACK_VIDEO);
    }

}
