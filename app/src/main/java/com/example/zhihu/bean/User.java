package com.example.zhihu.bean;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class User {
    private int uid;

    private String userCount;

    private String password;

    private String username;

    private String imageUrl;

    private String approveAnswerId;

    private int followingCount;

    private int followerCount;

    private int sex;

    private String qid;

    private String likeUid;

    private int praisedCount;

    private String backgroundUrl;

    private String collectAnswerId;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUserCount() {
        return userCount;
    }

    public void setUserCount(String userCount) {
        this.userCount = userCount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getLikeUid() {
        return likeUid;
    }

    public void setLikeUid(String likeUid) {
        this.likeUid = likeUid;
    }

    public int getPraisedCount() {
        return praisedCount;
    }

    public void setPraisedCount(int praisedCount) {
        this.praisedCount = praisedCount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    @BindingAdapter("bind:imageUrl")
    public static void setImage(ImageView image, String imageUrl){
        Glide.with(image.getContext()).load(imageUrl).into(image);
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getApproveAnswerId() {
        return approveAnswerId;
    }

    public void setApproveAnswerId(String approveAnswerId) {
        this.approveAnswerId = approveAnswerId;
    }

    public String getCollectAnswerId() {
        return collectAnswerId;
    }

    public void setCollectAnswerId(String collectAnswerId) {
        this.collectAnswerId = collectAnswerId;
    }
}
