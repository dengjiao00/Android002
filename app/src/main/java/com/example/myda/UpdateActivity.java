package com.example.myda;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.myda.dao.Note;
import com.example.myda.dao.NoteDao;

import com.example.myda.util.LinedEditText;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class UpdateActivity extends AppCompatActivity {
    EditText mUpdateDiaryEtTitle;
    LinedEditText mUpdateDiaryEtContent;
    TextView tv;
    private TextView sptext;
   // private Spinner spinner;
    ImageView iv_back;
    private ArrayAdapter<String> adapter;
    static Long position;
    //static String date1;
    Note note;
    NoteDao noteDao;
    private String n = "";

    public static void startActivity(Context context, Long id1, String title, String body,String author) {
        Intent intent = new Intent(context, UpdateActivity.class);
        intent.putExtra("id",id1);
        position=id1;
        intent.putExtra("title", title);
        intent.putExtra("content", body);
        intent.putExtra("author",author);
        context.startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update );

        sptext = findViewById(R.id.up_spText);
        //spinner = findViewById(R.id.up_sp);
        // 从SharedPreferences中加载之前的数据

//        String jsonString = sp.getString("name", "[]");
//        String lname = sp.getString("login_name","");
//        try {
//            JSONArray jsonArray= new JSONArray(jsonString);
//            List<String> names = new ArrayList<String>();
//            for (int i = 0;i<jsonArray.length();i++)
//            {
//                JSONObject itemObject =jsonArray.getJSONObject(i);
//                String name =itemObject.getString("name");
//                names.add(name);
//            }
//            int sel = names.indexOf(lname);
//            //将可选内容与ArrayAdapter连接起来
//            adapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,names);
//            //设置下拉列表的风格
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            //将adapter添加到spinner中
//            spinner.setAdapter(adapter);
//            //设置默认选项
//            spinner.setSelection(sel);
//            n = names.get(sel);
//            //添加点击选择的监听器
//            //view--点击的行对应的View(TextView)
//            //position--点击选择的行号
//
//            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    String s=((TextView)view).getText().toString();
//                    Toast.makeText(UpdateActivity.this, s, Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//
//                }
//            });
//
//        } catch (
//                JSONException e) {
//            e.printStackTrace();
//        }

        //设置返回按钮
//        iv_back = findViewById(R.id.common_iv_back);
//        iv_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //AlertDialog控件
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UpdateActivity.this);
//                alertDialogBuilder.setMessage("保存日记内容再退出？")
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                MainActivity.startActivity(UpdateActivity.this);
//                            }
//                        })
//                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                            }
//                        }).show();
//            }
//        });
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.startActivity(UpdateActivity.this);
            }
        });

        Intent intent = getIntent();
        tv = findViewById(R.id.up_spText);
        tv.setText(intent.getStringExtra("author"));
        String aa = tv.getText().toString();
        mUpdateDiaryEtTitle = findViewById(R.id.update_diary_et_title);
        mUpdateDiaryEtTitle.setText(intent.getStringExtra("title"));
        mUpdateDiaryEtContent = findViewById(R.id.update_diary_et_content);
        mUpdateDiaryEtContent.setText(intent.getStringExtra("content"));
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return  true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()){
            case R.id.back:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UpdateActivity.this);
                alertDialogBuilder.setMessage("确认退出？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.startActivity(UpdateActivity.this);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                break;
            case R.id.save:
                //String nowTime= GetDate.getDate().toString();
                //获取当前系统时间，传值到布局页面TextView上
                Date now = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                final String nowTime = dateFormat.format(now);
                SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
                String lname = sp.getString("login_name","");
                String title = mUpdateDiaryEtTitle.getText().toString();
                String body = mUpdateDiaryEtContent.getText().toString();
                String ntext = tv.getText().toString();
                String aa;
                if(!ntext.contains(lname)){
                    aa = ntext+"\n修改者："+lname;
                }
                else
                    aa = ntext;
                note=new Note();
                Note.MyApp.initGreenDao(UpdateActivity.this);
                noteDao = Note.MyApp.getDaoSession().getNoteDao() ;
                note.setId(position);
                note.setTitle(title);
                note.setContent(body);
                note.setAuthor(aa);
                note.setDate(nowTime);
                noteDao.update(note);
                MainActivity.startActivity(UpdateActivity.this);
                break;
            case R.id.delete:
                AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(UpdateActivity.this);
                alertDialogBuilder2.setMessage("确认删除？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                note=new Note();
                                Note.MyApp.initGreenDao(UpdateActivity.this);
                                noteDao = Note.MyApp.getDaoSession().getNoteDao();
                                noteDao.deleteByKey(position);
                                //noteDao.de
                                MainActivity.startActivity(UpdateActivity.this);
                                Toast.makeText(UpdateActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                break;
            default:
        }
        return true;
    }


}