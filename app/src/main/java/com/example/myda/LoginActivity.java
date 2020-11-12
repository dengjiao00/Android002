package com.example.myda;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private EditText nameEt;
    private EditText pwdEt;
    private Button login_btn;
    private Button regist_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pref = getSharedPreferences("data", 0);
        nameEt = findViewById(R.id.account);
        pwdEt = findViewById(R.id.pwd);
        login_btn = findViewById(R.id.login);
        regist_btn = findViewById(R.id.regist);

        regist_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
                startActivity(intent);
                finish();
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name1 = nameEt.getText().toString();
                String pwd1 = pwdEt.getText().toString();
                // 从SharedPreferences中加载之前的数据
                SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
                String jsonString = sp.getString("name", "[]");
                if (name1.isEmpty() || pwd1.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "姓名和密码都不能为空！！", Toast.LENGTH_SHORT).show();
                }
                try {
                    JSONArray jsonArray = new JSONArray(jsonString);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject itemObject = jsonArray.getJSONObject(i);
                        String name = itemObject.getString("name");
                        String pwd = itemObject.getString("pwd");
                        if (name1.isEmpty() || pwd1.isEmpty()) {
                            return;
                        }
                        if (name1.equals(name) && pwd1.equals(pwd)) {
                            SharedPreferences.Editor editor1 = getSharedPreferences("data",MODE_PRIVATE).edit();
                            editor1.putString("login_name",name1);
                            editor1.apply();
                            editor = pref.edit();
                            //提交数据
                            editor.commit();
                            //跳到主活动
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            return;
                        }
                        else if(name1.length()<6||pwd1.length()<2){
                            Toast.makeText(LoginActivity.this, "姓名不少于两位且密码不少于六位！！", Toast.LENGTH_SHORT).show();
                        }
                    }
                    Toast.makeText(LoginActivity.this, "您的登录名或密码有误！！", Toast.LENGTH_LONG ).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                String nameSP = pref.getString("name", "");
//                String password = pref.getString("pwd", "");
//                editor = pref.edit();
//                if (name.isEmpty()||pwd.isEmpty()){
//                    Toast.makeText(LoginActivity.this,"姓名和密码都不能为空！！",Toast.LENGTH_SHORT).show();
//                }
//                else if (name.equals(nameSP) && pwd.equals(password)) {
//                    if (rememberpass.isChecked()) {//选中了复选框
//                        //先存数据到SP
//                        editor.putString("account", name);
//                        editor.putString("pwd", pwd);
//                        editor.putBoolean("remember_pass", true);
//                    } else {
//                        //清空editor
//                        editor.putBoolean("remember_pass", false);
//                    }
//                    //提交数据
//                    editor.commit();
//                    //跳到主活动
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    Toast.makeText(LoginActivity.this, "您的登录名或密码有误！！", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }


}

