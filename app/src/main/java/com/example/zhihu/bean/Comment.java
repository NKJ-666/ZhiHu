package com.example.zhihu.bean;

public class Comment {
    private int cid;

    private int uid;

    private int aid;

    private String commentText;

    private int praisedCount;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public int getPraisedCount() {
        return praisedCount;
    }

    public void setPraisedCount(int praisedCount) {
        this.praisedCount = praisedCount;
    }
}
