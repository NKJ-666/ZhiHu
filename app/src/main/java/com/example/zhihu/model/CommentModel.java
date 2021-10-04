package com.example.zhihu.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.autofill.AutofillId;

import com.example.zhihu.bean.Answer;
import com.example.zhihu.bean.Comment;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.example.zhihu.util.DataTransformUtil;

import java.util.ArrayList;
import java.util.List;

public class CommentModel {
    private final MyDataBaseHelper helper;

    public CommentModel(MyDataBaseHelper helper){
        this.helper = helper;
    }

    public void insertCommentFromInstance(Comment comment){
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("uid", comment.getUid());
        values.put("aid", comment.getAid());
        values.put("commentText", comment.getCommentText());
        values.put("praisedCount", comment.getPraisedCount());
        database.insert("comment", null, values);
        database.close();
    }

    public void updateCommentFromInstance(Comment comment){
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("praisedCount", comment.getPraisedCount());
        database.update("comment", values, "cid = ?", new String[]{String.valueOf(comment.getCid())});
        database.close();
    }

    public Comment getCommentFromId(int cid){
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor cursor = database.query("comment", null, "cid = ?", new String[]{String.valueOf(cid)},null, null, null);
        cursor.moveToFirst();
        Comment comment = new Comment();
        comment.setCommentText(cursor.getString(cursor.getColumnIndex("commentText")));
        comment.setAid(cursor.getInt(cursor.getColumnIndex("aid")));
        comment.setCid(cursor.getInt(cursor.getColumnIndex("cid")));
        comment.setPraisedCount(cursor.getInt(cursor.getColumnIndex("praisedCount")));
        comment.setUid(cursor.getInt(cursor.getColumnIndex("uid")));
        database.close();
        return comment;
    }

    public List<Comment> getCommentFromAnswer(Answer answer){
        List<Comment> comments = new ArrayList<>();
        String [] cids = DataTransformUtil.convertToArray(answer.getCid());
        if (cids != null && cids.length > 0){
            for (String cid : cids){
                comments.add(getCommentFromId(Integer.parseInt(cid)));
            }
        }
        return comments;
    }

    public String getAllCommentIdFromAnswer(Answer answer){
        SQLiteDatabase database = helper.getWritableDatabase();
        StringBuilder builder = new StringBuilder();
        Cursor cursor = database.query("comment", new String[]{"cid"}, "aid = ?", new String[]{String.valueOf(answer.getAid())}, null, null, null);
        if (cursor.moveToFirst()){
            do {
                builder.append(cursor.getInt(cursor.getColumnIndex("cid"))).append(",");
            }while (cursor.moveToNext());
        }
        if (builder.length() != 0){
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }
}
