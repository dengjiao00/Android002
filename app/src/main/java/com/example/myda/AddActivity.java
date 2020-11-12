package com.example.myda;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myda.dao.Note;
import com.example.myda.dao.NoteDao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cc.trity.floatingactionbutton.FloatingActionButton;

public class AddActivity extends AppCompatActivity {
    Note note;
    NoteDao noteDao;
    EditText etTitle;
    EditText etbody;
    ImageView add_back;
    private String n = "";
    private TextView sptext;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;

    //活动传递,使用AddDiary.startActivity, 从context到添加日记
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AddActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        //设置开头文字
        TextView mTitle = findViewById(R.id.same_tv_title);
        mTitle.setText("添加日记");
        sptext = findViewById(R.id.spText);
        spinner = findViewById(R.id.sp1);
        // 从SharedPreferences中加载之前的数据
        SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
        String jsonString = sp.getString("name", "[]");
        String lname = sp.getString("login_name","");
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            List<String> names = new ArrayList<String>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject itemObject = jsonArray.getJSONObject(i);
                String name = itemObject.getString("name");
                names.add(name);
            }
            int sel = names.indexOf(lname);
            //将可选内容与ArrayAdapter连接起来
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, names);
            //设置下拉列表的风格
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //将adapter添加到spinner中
            spinner.setAdapter(adapter);
            //设置默认选项
            spinner.setSelection(sel);
            //添加点击选择的监听器
            //view--点击的行对应的View(TextView)
            //position--点击选择的行号

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String s = ((TextView) view).getText().toString();
                    n = s;
                    //  Toast.makeText(AddActivity.this, s, Toast.LENGTH_SHORT).show();
                    sptext.setText("选中: " + s);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } catch (
                JSONException e) {
            e.printStackTrace();
        }

        //设置返回按钮
        add_back = findViewById(R.id.same_iv_back);
        add_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AlertDialog控件
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddActivity.this);
                alertDialogBuilder.setMessage("直接退出？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.startActivity(AddActivity.this);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
            }
        });

        initDao();
        FloatingActionButton();

    }

    public void initDao() {
        note = new Note();
        Note.MyApp.initGreenDao(AddActivity.this);
        noteDao = Note.MyApp.getDaoSession().getNoteDao();
    }

    //插入表
    public void insertHote(String date, String title, String Content, String author) {
        note.setTitle(title);
        note.setContent(Content);
        note.setDate(date);
        note.setAuthor(author);
        noteDao.insert(note);//表hote  ， 数据库hoteDao
    }

    //设置监听悬浮按钮点击事件
    public void FloatingActionButton() {
        FloatingActionButton add = findViewById(R.id.add_diary_fab_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etTitle = findViewById(R.id.add_diary_et_title);
                etbody = findViewById(R.id.add_diary_et_content);
                //获取当前系统时间，传值到布局页面TextView上
                Date now = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                final String nowTime = dateFormat.format(now);
                //String date = GetDate.getDate().toString();
                String title = etTitle.getText().toString() + "";
                String body = etbody.getText().toString() + "";
                //AlertDialog控件
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddActivity.this);
                alertDialogBuilder.setMessage("保存日记？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //判断任何有一个的内容不为空，则调用insertHote
                                if (!title.equals("") || !body.equals("")) {
                                    insertHote(nowTime, title, body, n);
                                }
                                MainActivity.startActivity(AddActivity.this);
                                Toast.makeText(AddActivity.this, "已保存！", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.startActivity(AddActivity.this);
                            }
                        }).show();
            }
        });
        FloatingActionButton back = findViewById(R.id.add_diary_fab_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etTitle = findViewById(R.id.add_diary_et_title);
                etbody = findViewById(R.id.add_diary_et_content);
                //获取当前系统时间，传值到布局页面TextView上
                Date now = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                final String nowTime = dateFormat.format(now);
                //final String backdate = GetDate.getDate().toString();
                final String backtitle = etTitle.getText().toString();
                final String backbody = etbody.getText().toString();
                if (!backtitle.isEmpty() || !backbody.isEmpty()) {
                    //AlertDialog控件
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddActivity.this);
                    alertDialogBuilder.setMessage("保存日记内容再退出？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    insertHote(nowTime, backtitle, backbody, n);
                                    MainActivity.startActivity(AddActivity.this);
                                    Toast.makeText(AddActivity.this, "已保存！", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    MainActivity.startActivity(AddActivity.this);
                                }
                            }).show();
                } else {
                    MainActivity.startActivity(AddActivity.this);
                }
            }
        });
    }
}