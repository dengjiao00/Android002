package com.example.myda;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RegistActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText name;
    private EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        name =  findViewById(R.id.ed_name);
        password =  findViewById(R.id.et_pwd);
        Button button = findViewById(R.id.btn_submit);
        Button back = findViewById(R.id.btn_cancel);
        back.setOnClickListener(this);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_submit:
                String result = "";
                String nameStr = name.getText().toString();
                String pwdStr = password.getText().toString();
                if (nameStr.isEmpty() || pwdStr.isEmpty()) {
                    Toast.makeText(this, "姓名和密码都不能为空！！", Toast.LENGTH_SHORT).show();
                }
                else if(nameStr.length()<2||pwdStr.length()<6){
                    Toast.makeText(this, "姓名不少于两位且密码不少于六位！！", Toast.LENGTH_LONG).show();
                }
                else {
                    // 从SharedPreferences中加载之前的数据
                    SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
                    String jsonString = sp.getString("name", "[]");
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("name", nameStr);
                        jsonObject.put("pwd", pwdStr);

                        JSONArray jsonArray= new JSONArray(jsonString);
                        jsonArray.put(jsonObject);

                        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                        editor.putString("name", jsonArray.toString());
                        editor.commit();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
//                editor.putString("name", nameStr);
//                editor.putString("password", pwdStr);
//                editor.putString("sex", sex1.isChecked() ? "男" : "女");
//                editor.apply();
                    Toast.makeText(this, "注册成功！！", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(this, LoginActivity.class);
                    startActivity(intent1);
                    finish();
                }
                break;

        }
    }
}



//package com.example.myda;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.RadioButton;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//public class RegistActivity extends AppCompatActivity implements View.OnClickListener {
//    private EditText name;
//    private EditText password;
//    private int len = 0;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_regist);
//        name =  findViewById(R.id.ed_name);
//        password =  findViewById(R.id.et_pwd);
//        Button button = findViewById(R.id.btn_submit);
//        button.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_submit:
//                String result = "";
//                String nameStr = name.getText().toString();
//                String pwdStr = password.getText().toString();
//                if (nameStr.isEmpty() || pwdStr.isEmpty()) {
//                    Toast.makeText(this, "姓名和密码都不能为空！！", Toast.LENGTH_SHORT).show();
//                } else {
//                len++;
//                if (!nameStr.isEmpty()) result += nameStr;
//                if (!pwdStr.isEmpty()) result += pwdStr;
//                Toast.makeText(RegistActivity.this, result, Toast.LENGTH_LONG).show();
//                List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
//                Map<String,Object> map=new HashMap<String,Object>();
//                for (int i = len-1;i<len;i++){
//                    map.put("name", nameStr);
//                    map.put("pwd",pwdStr);
//                    list.add(map);
//                }
//
//
//                JSONArray mJsonArray = new JSONArray();
//                for (int j = 0;j<list.size();j++){
//                    Map<String, Object> itemMap = list.get(j);
//                    Iterator<Map.Entry<String, Object>> iterator = itemMap.entrySet().iterator();
//
//                    JSONObject object = new JSONObject();
//
//                    while (iterator.hasNext()) {
//                        Map.Entry<String, Object> entry = iterator.next();
//                        try {
//                            object.put(entry.getKey(), entry.getValue());
//                        } catch (JSONException e) {
//
//                        }
//                    }
//                    mJsonArray.put(object);
//                }
//                    SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
//                    editor.putString("name", mJsonArray.toString());
//                    editor.commit();
//
//
////                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
////                editor.putString("name", nameStr);
////                editor.putString("password", pwdStr);
////                editor.putString("sex", sex1.isChecked() ? "男" : "女");
////                editor.apply();
//                Toast.makeText(this, "注册成功！！", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(this, LoginActivity.class);
//                startActivity(intent);
//                finish();
//                }
//                break;
//
//        }
//    }
//}