package com.example.zhihu.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDataBaseHelper extends SQLiteOpenHelper {
    private final Context context;
    private static final String CREATE_USER = "create table user ("
            + "uid integer primary key autoincrement, "
            + "userAccount text not null, "
            + "password text not null, "
            + "username text not null default 'username', "
            + "followingCount integer not null default 0, "
            + "followerCount integer not null default 0, "
            + "imageUrl text not null default '/storage/emulated/0/$MuMu共享文件夹/1603088008624.jpg', "
            + "sex integer not null default 0, "
            + "backfroundUrl text, "
            + "qid text, "
            + "likeUid text, "
            + "praisedCount integer not null default 0)";

    private static final String CREATE_QUESTION = "create table question ("
            + "qid integer primary key autoincrement, "
            + "uid integer not null, "
            + "questionText text not null, "
            + "questionDetail text, "
            + "imageUrl text, "
            + "videoUrl text, "
            + "aid text)";

    private static final String CREATE_ANSWER = "create table answer ("
            + "aid integer primary key autoincrement, "
            + "uid integer not null, "
            + "qid integer not null, "
            + "answerText text not null, "
            + "imageUrl text, "
            + "videoUrl text, "
            + "type integer not null, "
            + "cid text, "
            + "praisedCount integer not null default 0, "
            + "collectedCount integer not null default 0, "
            + "commentCount integer not null default 0)";

    private static final String CREATE_COMMENT = "create table comment ("
            + "cid integer primary key autoincrement, "
            + "uid integer not null, "
            + "aid integer not null, "
            + "commentText text not null, "
            + "praisedCount integer not null default 0)";

    public MyDataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ANSWER);
        db.execSQL(CREATE_COMMENT);
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_QUESTION);
        Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
        db.execSQL(CREATE_USER);
    }
}
