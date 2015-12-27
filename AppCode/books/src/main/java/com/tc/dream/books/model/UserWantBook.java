package com.tc.dream.books.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 用户想要的书
 * Created by dream on 15/12/10.
 */
public class UserWantBook extends BmobObject{

    private String userName;   //用户名
    private String bookName;    //用户需要的书
    private String bookWriter;   //书的作者
    private String phone;  //用户联系电话
    private String tips;    //附加信息

    public BmobFile getPicBook() {
        return picBook;
    }

    public void setPicBook(BmobFile picBook) {
        this.picBook = picBook;
    }

    private BmobFile picBook;   //书籍图

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookWriter() {
        return bookWriter;
    }

    public void setBookWriter(String bookWriter) {
        this.bookWriter = bookWriter;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

}
