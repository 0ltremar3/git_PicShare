package com.example.picshare;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {
    private String nickName;//用户昵称

    public String getnickName(){
        return nickName;
    }

    public void setNickName(){
        this.nickName = nickName;
    }
}
