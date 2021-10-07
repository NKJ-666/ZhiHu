package com.example.zhihu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zhihu.R;
import com.example.zhihu.bean.Answer;
import com.example.zhihu.bean.Question;
import com.example.zhihu.bean.User;
import com.example.zhihu.databinding.QuestionItemImageBinding;
import com.example.zhihu.databinding.QuestionItemTextBinding;
import com.example.zhihu.databinding.QuestionItemVideoBinding;
import com.example.zhihu.databinding.QuestionNoAnswerBinding;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.example.zhihu.model.AnswerModel;
import com.example.zhihu.model.QuestionModel;
import com.example.zhihu.model.UserModel;
import com.example.zhihu.util.DataTransformUtil;
import com.example.zhihu.view.HomeFragment;
import com.example.zhihu.view.QuestionAndAnswerFrag;

import java.util.List;

public class HomeItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Question> questions;
    private final Context context;
    private final MyDataBaseHelper helper;
    private QuestionModel questionModel;
    private final AnswerModel answerModel;
    private final UserModel userModel;
    private final HomeFragment fragment;
    private User user;
    private static final int TYPE_TEXT = 0;
    private static final int TYPE_IMAGE = 1;
    private static final int TYPE_VIDEO = 2;
    private static final int TYPE_NO_ANSWER = 3;

    public HomeItemAdapter(Context context, List<Question> questions, MyDataBaseHelper helper, HomeFragment fragment, User user){
        this.context = context;
        this.questions = questions;
        this.helper = helper;
        this.fragment = fragment;
        this.user = user;
        questionModel = new QuestionModel(helper);
        answerModel = new AnswerModel(helper);
        userModel = new UserModel(helper);
    }

    static class ViewHolderText extends RecyclerView.ViewHolder {
        QuestionItemTextBinding textBinding;

        public ViewHolderText(QuestionItemTextBinding textBinding){
            super(textBinding.getRoot());
            this.textBinding = textBinding;
        }
    }

    static class ViewHolderImage extends RecyclerView.ViewHolder{
        QuestionItemImageBinding imageBinding;

        public ViewHolderImage(QuestionItemImageBinding imageBinding) {
            super(imageBinding.getRoot());
            this.imageBinding = imageBinding;
        }
    }

    static class ViewHolderVideo extends RecyclerView.ViewHolder{
        QuestionItemVideoBinding videoBinding;

        public ViewHolderVideo(QuestionItemVideoBinding videoBinding) {
            super(videoBinding.getRoot());
            this.videoBinding = videoBinding;
        }
    }

    static class ViewHolderNoAnswer extends RecyclerView.ViewHolder{
        QuestionNoAnswerBinding binding;

        public ViewHolderNoAnswer(QuestionNoAnswerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @Override
    public int getItemViewType(int position) {
        String[] aid = DataTransformUtil.convertToArray(questions.get(position).getAid());
        Answer answer = null;
        if (aid == null)
            return TYPE_NO_ANSWER;
        else if (aid.length != 0){
            answer = answerModel.getAnswerFromId(Integer.parseInt(aid[0]));
            return answer.getType();
        }else{
            return TYPE_TEXT;
        }
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_TEXT){
            QuestionItemTextBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.question_item_text, parent, false);
            return new ViewHolderText(binding);
        }else if (viewType == TYPE_IMAGE){
            QuestionItemImageBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.question_item_image, parent, false);
            return new ViewHolderImage(binding);
        }else if(viewType == TYPE_VIDEO){
            QuestionItemVideoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.question_item_video, parent, false);
            return new ViewHolderVideo(binding);
        }else {
            QuestionNoAnswerBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.question_no_answer, parent, false);
            return new ViewHolderNoAnswer(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Question question = questions.get(position);
        if (holder instanceof ViewHolderText) {
            ViewHolderText holderText = (ViewHolderText) holder;
            User mUser = userModel.queryUserFromId(question.getUid());
            holderText.textBinding.setUser(mUser);
            String[] aid = DataTransformUtil.convertToArray(question.getAid());
            Answer answer = null;
            if (aid != null && aid.length != 0) {
                answer = answerModel.getAnswerFromId(Integer.parseInt(aid[0]));
                holderText.textBinding.setAnswer(answer);
            }
            holderText.textBinding.setQuestion(question);
            holderText.textBinding.totalLayout.setOnClickListener(v -> fragment.requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.home_main_fragment, new QuestionAndAnswerFrag(helper, question, user))
                    .commit());
        }else if(holder instanceof ViewHolderImage){
            ViewHolderImage holderImage = (ViewHolderImage) holder;
            User mUser = userModel.queryUserFromId(question.getUid());
            holderImage.imageBinding.setUser(mUser);
            String[] aid = DataTransformUtil.convertToArray(question.getAid());
            Answer answer = null;
            if (aid != null && aid.length != 0) {
                answer = answerModel.getAnswerFromId(Integer.parseInt(aid[0]));
                holderImage.imageBinding.setAnswer(answer);
                Glide.with(holderImage.imageBinding.answerImage.getContext()).load(DataTransformUtil.convertToArray(answer.getImageUrl())[0]).into(holderImage.imageBinding.answerImage);
            }
            holderImage.imageBinding.setQuestion(question);
            holderImage.imageBinding.totalLayout.setOnClickListener(v -> fragment.requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.home_main_fragment, new QuestionAndAnswerFrag(helper, question, user))
                    .commit());
        }else if (holder instanceof ViewHolderVideo){
            ViewHolderVideo holderVideo = (ViewHolderVideo) holder;
            User mUser = userModel.queryUserFromId(question.getUid());
            holderVideo.videoBinding.setUser(mUser);
            String[] aid = DataTransformUtil.convertToArray(question.getAid());
            Answer answer = null;
            if (aid != null && aid.length != 0) {
                answer = answerModel.getAnswerFromId(Integer.parseInt(aid[0]));
                holderVideo.videoBinding.setAnswer(answer);
                holderVideo.videoBinding.video.setVideoPath(DataTransformUtil.convertToArray(answer.getVideoUrl())[0]);
                holderVideo.videoBinding.video.seekTo(100);
            }
            holderVideo.videoBinding.setQuestion(question);
            holderVideo.videoBinding.totalLayout.setOnClickListener(v -> fragment.requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.home_main_fragment, new QuestionAndAnswerFrag(helper, question, user))
                    .commit());
        }else {
            ViewHolderNoAnswer holderNoAnswer = (ViewHolderNoAnswer) holder;
            holderNoAnswer.binding.setQuestion(question);
            holderNoAnswer.binding.totalLayout.setOnClickListener(v -> fragment.requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.home_main_fragment, new QuestionAndAnswerFrag(helper, question, user))
                    .commit());
        }
    }

}
