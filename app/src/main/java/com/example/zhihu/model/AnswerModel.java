package com.example.zhihu.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.example.zhihu.bean.Answer;
import com.example.zhihu.bean.Question;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.example.zhihu.util.DataTransformUtil;

import java.util.ArrayList;
import java.util.List;

public class AnswerModel {
    private MyDataBaseHelper helper;

    public AnswerModel(MyDataBaseHelper helper){
        this.helper = helper;
    }

    public void insertAnswerFromInstance(Answer answer){
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("qid", answer.getQid());
        values.put("cid", answer.getCid());
        values.put("answerText", answer.getAnswerText());
        values.put("imageUrl", answer.getImageUrl());
        values.put("videoUrl", answer.getVideoUrl());
        values.put("uid", answer.getUid());
        values.put("type", answer.getType());
        values.put("praisedCount", answer.getPraisedCount());
        values.put("commentCount", answer.getCommentCount());
        database.insert("answer", null, values);
        database.close();
    }

    public Answer getAnswerFromId(int aid){
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor cursor = database.query("answer", null, "aid = ?", new String[]{String.valueOf(aid)},null, null, null);
        cursor.moveToFirst();
        Answer answer = new Answer();
        answer.setAnswerText(cursor.getString(cursor.getColumnIndex("answerText")));
        answer.setAid(cursor.getInt(cursor.getColumnIndex("aid")));
        answer.setCid(cursor.getString(cursor.getColumnIndex("cid")));
        answer.setCommentCount(cursor.getInt(cursor.getColumnIndex("commentCount")));
        answer.setPraisedCount(cursor.getInt(cursor.getColumnIndex("praisedCount")));
        answer.setImageUrl(cursor.getString(cursor.getColumnIndex("imageUrl")));
        answer.setVideoUrl(cursor.getString(cursor.getColumnIndex("videoUrl")));
        answer.setQid(cursor.getInt(cursor.getColumnIndex("qid")));
        answer.setUid(cursor.getInt(cursor.getColumnIndex("uid")));
        answer.setType(cursor.getInt(cursor.getColumnIndex("type")));
        database.close();
        return answer;
    }

    public void updateAnswerFromInstance(Answer answer){
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cid", answer.getCid());
        values.put("commentCount", answer.getCommentCount());
        values.put("praisedCount", answer.getCommentCount());
        database.update("answer", values, "aid = ?", new String[]{String.valueOf(answer.getAid())});
        database.close();
    }

    public List<Answer> getAnswersFromQuestion(Question question){
        List<Answer> answers = new ArrayList<>();
        String [] aids = DataTransformUtil.convertToArray(question.getAid());
        if (aids != null && aids.length > 0){
            for (String id : aids){
                answers.add(getAnswerFromId(Integer.parseInt(id)));
            }
        }
        return answers;
    }

    public List<String> getAllAnswerIdFromQuestion(Question question){
        List<String> aids = new ArrayList<>();
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor cursor = database.query("answer", new String[]{"aid"}, "qid = ?", new String[]{String.valueOf(question.getQid())}, null, null, null);
        if (cursor.moveToFirst()){
            do {
                aids.add(cursor.getString(cursor.getColumnIndex("aid")));
            }while (cursor.moveToNext());
        }
        return aids;
    }

    public void updateAnswerCid(Answer answer){
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cid", answer.getCid());
        database.update("answer", values, "aid = ?", new String[]{String.valueOf(answer.getAid())});
        database.close();
    }

    public void updateAnswerPraisedCount(Answer answer){
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("praisedCount", answer.getPraisedCount());
        database.update("answer", values, "aid = ?", new String[]{String.valueOf(answer.getAid())});
        database.close();
    }

    public void updateCommentCount(@NonNull Answer answer){
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("commentCount", answer.getCommentCount());
        database.update("answer", values, "aid = ?", new String[]{String.valueOf(answer.getCommentCount())});
        database.close();
    }
}
