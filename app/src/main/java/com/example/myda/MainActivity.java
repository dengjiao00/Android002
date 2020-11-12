 package com.example.myda;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myda.dao.Note;
import com.example.myda.dao.NoteDao;

import java.util.ArrayList;
import java.util.List;


import cc.trity.floatingactionbutton.FloatingActionButton;

import static com.example.myda.dao.Note.MyApp.initGreenDao;
import static com.example.myda.dao.Note.MyApp.getDaoSession;



public class MainActivity extends AppCompatActivity {

    NoteDao noteDao;  //连接数据库
    private List<Note> noteData; //把数据变成列表
    private RecyclerView mRecyclerView;
    private DiaryAdapter diaryAdapter;


    //显示intent  使用方法：MainActivity.startActivity(从哪个activity回到MainActivity)
    public static void startActivity(Context context)
    {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        //onCreate()是一个活动被创建时必定要执行的方法
        super.onCreate(savedInstanceState);

        //给当前的活动引入activity_main布局
        setContentView(R.layout.activity_main);

        //规定标题的格式，设置其中的文字
        TextView mTitle=(TextView)findViewById(R.id.common_tv_title);//获取到在commo_title.xml定义的组件common_tv_title
        mTitle.setText("日记");
//从主键到搜索界面
        Button searchButton = (Button) findViewById(R.id.rightButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.startActivity(MainActivity.this);
            }
        });
        //规定返回按键，common_iv_back在commo_title.xml中
        ImageView mBack=(ImageView)findViewById(R.id.common_iv_back);
        //主界面隐藏这个返回按键
        mBack.setVisibility(View.INVISIBLE);
        //悬浮按钮main_fab_enter_edit
        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.main_fab_enter_edit);
        //使用匿名类的方式注册悬浮按钮的监听事件
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                //调用AddDiary.java中的startActivity(),从MainActivity跳转到添加日记的Activity和xml
                AddActivity.startActivity(MainActivity.this);
            }
        });
        //初始化recyclerview
        mRecyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview
        initData();
        //监听修改哪个日记  因为RecyclerView
        diaryAdapter.setOnItemClickListner(new DiaryAdapter.ItemClickListner()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                String title=noteData.get(position).getTitle();
                String body=noteData.get(position).getContent();
                Long id=noteData.get(position).getId();
                String author = noteData.get(position).getAuthor();
                //跳转UpdateDiary的java和xml
                UpdateActivity.startActivity(MainActivity.this, id, title, body,author);
            }
        });
    }
    //初始化数据  RecyclerView的初始化
    private void initData(){
        noteData=new ArrayList<Note>();
        initGreenDao(this);
        noteDao=getDaoSession().getNoteDao();
        noteData=noteDao.queryBuilder().orderDesc(NoteDao.Properties.Date).list();
        diaryAdapter=new DiaryAdapter(noteData);
        mRecyclerView.setAdapter(diaryAdapter);
    }

}
