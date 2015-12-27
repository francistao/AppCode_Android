package com.tc.dream.books.model;

/**
 * 读书笔记模型类
 * Created by dream on 15/12/12.
 */
public class NoteItems {

    public long id;
    public String title;
    public String last_reviewed;
    public String total_reviews;
    public String content;

    public NoteItems(String title, String last_reviewed, String total_reviews, String content) {
        this.title = title;
        this.last_reviewed = last_reviewed;
        this.total_reviews = total_reviews;
        this.content = content;
    }
}
