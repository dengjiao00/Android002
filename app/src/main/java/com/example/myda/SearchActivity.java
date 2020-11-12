package com.example.myda;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myda.dao.Note;
import com.example.myda.dao.NoteDao;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static com.example.myda.dao.Note.MyApp.getDaoSession;
import static com.example.myda.dao.Note.MyApp.initGreenDao;

public class SearchActivity extends AppCompatActivity {

    //存储搜索的ID
    EditText searchid;

    NoteDao noteDao;  //连接数据库
    private List<Note> noteData = new ArrayList<>(); //把数据变成列表
    private RecyclerView mRecyclerView;
    private DiaryAdapter diaryAdapter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mRecyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview
////        noteDao = getDaoSession().getNoteDao();
////        noteData = noteDao.queryBuilder().where(NoteDao.Properties.Title.like("%" + "1" + "%")).orderDesc(NoteDao.Properties.Date).list();


        Button back = findViewById(R.id.leftButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SearchActivity.this);
                alertDialogBuilder.setMessage("退出查询？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.startActivity(SearchActivity.this);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }
        });

        ImageButton search = findViewById(R.id.search);
        EditText editText = findViewById(R.id.search_input);
        //查询按钮
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.startActivity(SearchActivity.this);
                // TODO 1. 获取EditText中的文本
                String inputString = editText.getText().toString();
                // TODO 2. 调用SQL获取结果
                noteDao = getDaoSession().getNoteDao();
                List<Note> list = noteDao.queryBuilder().where(NoteDao.Properties.Title.like("%" +inputString + "%")).orderDesc(NoteDao.Properties.Date).list();
//                for (int i=0;i<list.size();i++){
//                    noteData.add(list.get(i));
//                    diaryAdapter.setNoteData(noteData);
//                }
//                list.clear();
                mRecyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this,LinearLayoutManager.VERTICAL,false));
                noteData.addAll(list);
                diaryAdapter=new DiaryAdapter(noteData);
                mRecyclerView.setAdapter(diaryAdapter);
//                diaryAdapter.notifyDataSetChanged();
//                mRecyclerView.setAdapter(diaryAdapter);

//                mRecyclerView.setAdapter(new DiaryAdapter(noteData));
//                noteDao = getDaoSession().getNoteDao();
//                noteData = noteDao.queryBuilder().where(NoteDao.Properties.Title.like("%" + inputString + "%")).orderDesc(NoteDao.Properties.Date).list();
//                diaryAdapter.setNoteData(noteData);
            }

        });

/*
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
                initRecyclerView();
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerViewClickListener(this, mRecyclerView, new RecyclerViewClickListener.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(View view, int position) {
                        Long ID = noteData.get(position).getId();
                        //String id = Integer.toString(ID);
                        Intent intent = new Intent(search.this,update.class);
                     //   intent.putExtra("id",id);

                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, final int position) {
                        AlertDialog.Builder ab = new AlertDialog.Builder(search.this);
                        ab.setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String title=noteData.get(position).getTitle();
                                String body=noteData.get(position).getContent();
                                Long id=noteData.get(position).getId();
                                //跳转UpdateDiary的java和xml
                                update.startActivity(search.this, id, title, body);

                            }
                        });

                        ab.setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //
                            }
                        });

                        ab.setMessage("是否删除？");
                        ab.setTitle("提示");
                        ab.show();
                    }
                }));




*/


    }


    //初始化数据和RecyclerView的初始化
    private void initRecyclerView() {
        noteData = new ArrayList<Note>();
        initGreenDao(this);
        noteDao = getDaoSession().getNoteDao();


        noteData = noteDao.queryBuilder().orderDesc(NoteDao.Properties.Date).list();

        diaryAdapter = new DiaryAdapter(noteData);
        mRecyclerView.setAdapter(diaryAdapter);
    }


}
