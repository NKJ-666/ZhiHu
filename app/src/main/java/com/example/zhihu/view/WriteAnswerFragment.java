package com.example.zhihu.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.zhihu.R;
import com.example.zhihu.bean.Answer;
import com.example.zhihu.bean.Question;
import com.example.zhihu.bean.User;
import com.example.zhihu.databinding.WriteAnswerFragmentBinding;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.example.zhihu.util.DataTransformUtil;
import com.example.zhihu.util.GetRealUriUtil;
import com.example.zhihu.viewModel.WriteAnswerViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class WriteAnswerFragment extends Fragment {
    private WriteAnswerFragmentBinding binding;
    private MyDataBaseHelper helper;
    private Question question;
    private User user;
    private WriteAnswerViewModel viewModel;
    private static final int GET_BACK_IMAGE = 2;
    private static final int GET_BACK_VIDEO = 1;
    private List<String> imageUrls = new ArrayList<>();
    private List<String> videoUrls = new ArrayList<>();
    private Answer answer;

    public WriteAnswerFragment(MyDataBaseHelper helper, Question question, User user){
        this.helper = helper;
        this.user = user;
        this.question = question;
        answer = new Answer();
        answer.setUid(user.getUid());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.write_answer_fragment, container, false);
        init();
        return binding.getRoot();
    }

    private void init(){
        viewModel = new WriteAnswerViewModel(getContext(), helper, binding, this, question, user);
        viewModel.init();
        binding.sendAnswerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answerText = binding.answerText.getText().toString();
                if (answerText.isEmpty()){
                    Toast.makeText(getContext(), "回答不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    answer.setImageUrl(DataTransformUtil.convertToString(imageUrls.toArray(new String[imageUrls.size()])));
                    answer.setVideoUrl(DataTransformUtil.convertToString(videoUrls.toArray(new String[videoUrls.size()])));
                    answer.setAnswerText(answerText);
                    answer.setQid(question.getQid());
                    int length1 = imageUrls.size();
                    int length2 = videoUrls.size();
                    if (length1 == 0 && length2 == 0)
                        answer.setType(0);
                    else if (length1 != 0){
                        answer.setType(1);
                    }else{
                        answer.setType(2);
                    }
                    viewModel.insertAnswer(answer);
                    List<String> aids = viewModel.getAllAnswerIdFromQuestion(question);
                    StringBuilder questionAid = new StringBuilder();
                    for (String aid : aids){
                        questionAid.append(",").append(aid);
                    }
                    if (questionAid.charAt(0) == ','){
                        questionAid.deleteCharAt(0);
                    }
                    question.setAid(questionAid.toString());
                    viewModel.updateQuestionAid(question);
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.home_main_fragment, new QuestionAndAnswerFrag(helper, question, user))
                            .commit();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case GET_BACK_IMAGE:{
                    if (data != null){
                        String path = GetRealUriUtil.getImageRealPath(getContext(), data.getData());
                        viewModel.addImageView(path);
                        imageUrls.add(path);
                    }
                    break;
                }
                case GET_BACK_VIDEO:{
                    if (data != null){
                        String path = GetRealUriUtil.getImageRealPath(getContext(), data.getData());
                        viewModel.addVideoView(path);
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
}
