package com.example.myda.bean;

public class DiaryBean {
    private String date;
    private String title;
    private String content;
    private String author;
    private String tag;



    public DiaryBean(String date, String title, String content, String author, String tag) {
        this.date = date;
        this.title = title;
        this.content = content;
        this.author = author;
        this.tag = tag;

    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
