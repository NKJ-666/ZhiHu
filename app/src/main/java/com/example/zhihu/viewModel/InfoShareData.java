package com.example.zhihu.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InfoShareData extends ViewModel {
    private MutableLiveData<Integer> followingCount = new MutableLiveData<>();

    private MutableLiveData<Integer> followerCount = new MutableLiveData<>();

    private MutableLiveData<Integer> approveCount = new MutableLiveData();


    public MutableLiveData<Integer> getApproveCount() {
        return approveCount;
    }

    public MutableLiveData<Integer> getFollowerCount() {
        return followerCount;
    }

    public MutableLiveData<Integer> getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(Integer followingCount){
        this.followerCount.setValue(followingCount);
    }

    public void setFollowerCount(Integer followerCount){
        this.followerCount.setValue(followerCount);
    }

    public void setApproveCount(Integer approveCount){
        this.approveCount.setValue(approveCount);
    }
}
