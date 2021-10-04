package com.example.zhihu.viewModel;

import android.content.Context;

import com.example.zhihu.bean.Comment;
import com.example.zhihu.bean.User;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.example.zhihu.model.CommentModel;
import com.example.zhihu.model.UserModel;

public class CommentItemViewModel {
    private UserModel userModel;

    private Context context;

    private CommentModel commentModel;

    public CommentItemViewModel(Context context, MyDataBaseHelper helper){
        this.context = context;
        userModel = new UserModel(helper);
        commentModel = new CommentModel(helper);
    }

    public User getUserFromComment(Comment comment){
        return userModel.queryUserFromId(comment.getUid());
    }
}
