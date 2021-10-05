package com.example.zhihu.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.zhihu.bean.User;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.example.zhihu.util.DataTransformUtil;

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
            user.setApproveAnswerId(cursor.getString(cursor.getColumnIndex("approveAnswerId")));
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
            user.setCollectAnswerId(cursor.getString(cursor.getColumnIndex("collectAnswerId")));
            database.close();
        }
        return user;
    }

    public void updateUserFromInstance(User user){
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("approveAnswerId", user.getApproveAnswerId());
        values.put("username", user.getUsername());
        values.put("qid", user.getQid());
        values.put("followerCount", user.getFollowerCount());
        values.put("praisedCount", user.getPraisedCount());
        values.put("likeUid", user.getLikeUid());
        values.put("followingCount", user.getFollowingCount());
        values.put("sex", user.getSex());
        values.put("imageUrl", user.getImageUrl());
        values.put("backfroundUrl", user.getBackgroundUrl());
        values.put("collectAnswerId", user.getCollectAnswerId());
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
            user.setApproveAnswerId(cursor.getString(cursor.getColumnIndex("approveAnswerId")));
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
            user.setCollectAnswerId(cursor.getString(cursor.getColumnIndex("collectAnswerId")));
            database.close();
            return user;
        }
    }

    public void updateAnswerId(User user){
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("approveAnswerId", user.getApproveAnswerId());
        database.update("user", values, "uid = ?", new String[]{String.valueOf(user.getUid())});
        database.close();
    }

    public String [] getAnswerId(User user){
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor cursor = database.query("user", new String[]{"approveAnswerId"}, "uid = ?", new String[]{String.valueOf(user.getUid())},null, null, null);
        if (cursor.moveToFirst()){
            String [] id = DataTransformUtil.convertToArray(cursor.getString(cursor.getColumnIndex("approveAnswerId")));
            database.close();
            return id;
        }
        return null;
    }

    public String getStringAnswerId(User user){
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor cursor = database.query("user", new String[]{"approveAnswerId"}, "uid = ?", new String[]{String.valueOf(user.getUid())},null, null, null);
        if (cursor.moveToFirst()){
            String id = cursor.getString(cursor.getColumnIndex("approveAnswerId"));
            database.close();
            return id;
        }
        return null;
    }

    public void updateUserFollow(User user){
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("followingCount", user.getFollowingCount());
        database.update("user", values, "uid = ?", new String[]{String.valueOf(user.getUid())});
        database.close();
    }

    public void updateUserFollowed(User user){
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("followerCount", user.getFollowerCount());
        database.update("user", values, "uid = ?", new String[]{String.valueOf(user.getUid())});
        database.close();
    }

    public void updateApproved(User user){
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("praisedCount", user.getPraisedCount());
        database.update("user", values, "uid = ?", new String[]{String.valueOf(user.getUid())});
        database.close();
    }

    public void updateLikeUid(User user){
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("likeUid", user.getLikeUid());
        database.update("user", values, "uid = ?", new String[]{String.valueOf(user.getUid())});
        database.close();
    }

    public String getLikeUid(User user){
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor cursor = database.query("user", new String[]{"likeUid"}, "uid = ?", new String[]{String.valueOf(user.getUid())}, null, null, null);
        if (cursor.moveToFirst()){
            String ids = cursor.getString(cursor.getColumnIndex("likeUid"));
            return ids;
        }
        return null;
    }

    public void updateCollectAnswer(User user){
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("collectAnswerId", user.getCollectAnswerId());
        database.update("user", values, "uid = ?", new String[]{String.valueOf(user.getUid())});
        database.close();
    }

    public String getCollectId(User user){
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor cursor = database.query("user", new String[]{"collectAnswerId"}, "uid = ?", new String[]{String.valueOf(user.getUid())}, null, null, null);
        if (cursor.moveToFirst()){
            String ids = cursor.getString(cursor.getColumnIndex("collectAnswerId"));
            database.close();
            return ids;
        }
        return null;
    }
}
