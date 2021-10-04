package com.example.zhihu.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.zhihu.bean.User;
import com.example.zhihu.helper.MyDataBaseHelper;

public class UserModel {
    private MyDataBaseHelper helper;

    public UserModel(MyDataBaseHelper helper){
        this.helper = helper;
    }

    public void insertUser(String userAccount, String password){
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userAccount", userAccount);
        values.put("password", password);
        database.insert("user", null, values);
        database.close();
    }

    public User queryUserFromId(int uid){
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor cursor = database.query("user", null, "uid = ?", new String[]{String.valueOf(uid)}, null, null, null);
        User user = null;
        if(cursor.moveToFirst()){
            user = new User();
            user.setUid(uid);
            user.setUserCount(cursor.getString(cursor.getColumnIndex("userAccount")));
            user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            user.setQid(cursor.getString(cursor.getColumnIndex("qid")));
            user.setFollowerCount(cursor.getInt(cursor.getColumnIndex("followerCount")));
            user.setPraisedCount(cursor.getInt(cursor.getColumnIndex("praisedCount")));
            user.setLikeUid(cursor.getString(cursor.getColumnIndex("likeUid")));
            user.setFollowingCount(cursor.getInt(cursor.getColumnIndex("followingCount")));
            user.setBackgroundUrl(cursor.getString(cursor.getColumnIndex("backfroundUrl")));
            user.setSex(cursor.getInt(cursor.getColumnIndex("sex")));
            user.setImageUrl(cursor.getString(cursor.getColumnIndex("imageUrl")));
            database.close();
        }
        return user;
    }

    public void updateUserFromInstance(User user){
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("qid", user.getQid());
        values.put("followerCount", user.getFollowerCount());
        values.put("praisedCount", user.getPraisedCount());
        values.put("likeUid", user.getLikeUid());
        values.put("followingCount", user.getFollowingCount());
        values.put("sex", user.getSex());
        values.put("imageUrl", user.getImageUrl());
        values.put("backfroundUrl", user.getBackgroundUrl());
        database.update("user", values, "uid = ?", new String[]{String.valueOf(user.getUid())});
        database.close();
    }

    public User queryUserFromAccount(String accountNumber){
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor cursor = database.query("user", null, "userAccount = ?", new String[]{accountNumber}, null, null, null);
        if (!cursor.moveToFirst())
            return null;
        else {
            User user = new User();
            cursor.moveToFirst();
            user.setUid(cursor.getInt(cursor.getColumnIndex("uid")));
            user.setUserCount(cursor.getString(cursor.getColumnIndex("userAccount")));
            user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            user.setQid(cursor.getString(cursor.getColumnIndex("qid")));
            user.setFollowerCount(cursor.getInt(cursor.getColumnIndex("followerCount")));
            user.setPraisedCount(cursor.getInt(cursor.getColumnIndex("praisedCount")));
            user.setLikeUid(cursor.getString(cursor.getColumnIndex("likeUid")));
            user.setFollowingCount(cursor.getInt(cursor.getColumnIndex("followingCount")));
            user.setBackgroundUrl(cursor.getString(cursor.getColumnIndex("backfroundUrl")));
            user.setSex(cursor.getInt(cursor.getColumnIndex("sex")));
            user.setImageUrl(cursor.getString(cursor.getColumnIndex("imageUrl")));
            database.close();
            return user;
        }
    }
}
