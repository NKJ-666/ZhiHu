package com.example.zhihu.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.zhihu.bean.Question;
import com.example.zhihu.bean.User;
import com.example.zhihu.helper.MyDataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class QuestionModel {
    private final MyDataBaseHelper helper;

    public QuestionModel(MyDataBaseHelper helper){
        this.helper = helper;
    }

    public Question getQuestionFromId(int qid){
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor cursor = database.query("question", null, "qid = ?", new String[]{String.valueOf(qid)}, null, null, null);
        cursor.moveToFirst();
        Question question = new Question();
        question.setQuestionText(cursor.getString(cursor.getColumnIndex("questionText")));
        question.setAid(cursor.getString(cursor.getColumnIndex("aid")));
        question.setQid(cursor.getInt(cursor.getColumnIndex("qid")));
        question.setUid(cursor.getInt(cursor.getColumnIndex("uid")));
        question.setImageUrl(cursor.getString(cursor.getColumnIndex("imageUrl")));
        question.setVideoUrl(cursor.getString(cursor.getColumnIndex("videoUrl")));
        question.setQuestionDetail(cursor.getString(cursor.getColumnIndex("questionDetail")));
        database.close();
        return question;
    }

    public void insertQuestionFromInstance(Question question){
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("uid", question.getUid());
        values.put("questionText", question.getQuestionText());
        values.put("imageUrl", question.getImageUrl());
        values.put("videoUrl", question.getVideoUrl());
        values.put("aid", question.getAid());
        values.put("questionDetail", question.getQuestionDetail());
        database.insert("question", null, values);
        database.close();
    }

    public List<Question> getAllQuestion(){
        SQLiteDatabase database = helper.getWritableDatabase();
        List<Question> questions = new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from question order by qid desc", null);
        if (cursor.moveToFirst()){
            do {
                Question question = new Question();
                question.setQuestionText(cursor.getString(cursor.getColumnIndex("questionText")));
                question.setAid(cursor.getString(cursor.getColumnIndex("aid")));
                question.setQid(cursor.getInt(cursor.getColumnIndex("qid")));
                question.setUid(cursor.getInt(cursor.getColumnIndex("uid")));
                question.setImageUrl(cursor.getString(cursor.getColumnIndex("imageUrl")));
                question.setVideoUrl(cursor.getString(cursor.getColumnIndex("videoUrl")));
                question.setQuestionDetail(cursor.getString(cursor.getColumnIndex("questionDetail")));
                questions.add(question);
            }while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return questions;
    }

    public void updateQuestionAid(Question question){
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("aid", question.getAid());
        database.update("question", values, "qid = ?", new String[]{String.valueOf(question.getQid())});
        database.close();
    }

    public List<Question> getAllUserQuestion(User user){
        SQLiteDatabase database = helper.getWritableDatabase();
        List<Question> questions = new ArrayList<>();
        Cursor cursor = database.query("question", new String[]{"questionText", "qid", "uid"}, "uid = ?", new String[]{String.valueOf(user.getUid())}, null, null, "qid desc");
        if (cursor.moveToFirst()){
            do {
                Question question = new Question();
                question.setUid(cursor.getInt(cursor.getColumnIndex("uid")));
                question.setQuestionText(cursor.getString(cursor.getColumnIndex("questionText")));
                questions.add(question);
            }while (cursor.moveToNext());
        }
        return questions;
    }

    public List<Question> getAllQuestionText(){
        SQLiteDatabase database = helper.getWritableDatabase();
        List<Question> questionTexts = new ArrayList<>();
        Cursor cursor = database.query("question", new String[]{"questionText", "qid"}, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do {
                Question question = new Question();
                question.setQid(cursor.getInt(cursor.getColumnIndex("qid")));
                question.setQuestionText(cursor.getString(cursor.getColumnIndex("questionText")));
                questionTexts.add(question);
            }while (cursor.moveToNext());
        }
        return questionTexts;
    }
}
