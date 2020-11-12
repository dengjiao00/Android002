package com.example.myda.dao;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;


@Entity
public class Note {
    @Id(autoincrement = true)
    private Long id;
    private String content;
    private String date;
    private String title;
    private String author;

    @Generated(hash = 189944527)
    public Note(Long id, String title, String content, String author,String date) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.title = title;
        this.author = author;
    }
    @Generated(hash = 1272611929)
    public Note() {
    }
    public Long getId() {
        return this.id;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }



    public static class MyApp extends Application {
        public static DaoSession daoSession;
        private static MyApp instance;
        //初始化GreenDao
        public static void initGreenDao(Context context){
            //创建数据库diary.db
            DaoMaster.DevOpenHelper helper=new DaoMaster.DevOpenHelper(context,"diary_01.db",null);
            //获取可写数据库
            SQLiteDatabase db=helper.getWritableDatabase();
            //获取数据库对象
            DaoMaster daoMaster=new DaoMaster(db);
            //获取Dao对象管理者
            daoSession=daoMaster.newSession();
            //获取NoteDao
            //NoteDao NoteDao=daoSession.getNoteDao();
        }

        public static DaoSession getDaoSession(){
            return daoSession;
        }
    }


}