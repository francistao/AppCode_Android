package com.tc.dream.books.model;

import cn.bmob.v3.BmobObject;

/**
 * 用户反馈信息
 * Created by dream on 15/12/11.
 */
public class FeedBack extends BmobObject{

    private String username;
    private String phone;
    private String email;
    private String content;

    public String getUsername(String username) {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
