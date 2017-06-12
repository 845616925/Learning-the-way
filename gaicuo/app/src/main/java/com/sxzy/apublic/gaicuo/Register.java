package com.sxzy.apublic.gaicuo;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    EditText usename, password1, password2, phone;
    Button OK, NO;
    boolean p1, p2, p3;
    SharedPreferences sp, sp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register);
        initview();
        sp = getSharedPreferences("Password", MODE_PRIVATE);


        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Phone = phone.getText().toString();
                String us = usename.getText().toString();
                String pw1 = password1.getText().toString().trim();
                String pw2 = password2.getText().toString().trim();

                boolean isok = okPhone(Phone);
                if (!us.isEmpty()) {
                    p1 = true;
                    if (!pw1.isEmpty() && !pw2.isEmpty()) {
                        if (pw1.equals(pw2)) {
                            Toast.makeText(v.getContext(), "两次输入相同 ", Toast.LENGTH_SHORT).show();
                            p2 = true;
                            if (!Phone.isEmpty()) {
                                if (isok) {
                                    p3 = true;
                                }
                                Toast.makeText(v.getContext(), "手机号：" + Phone + "是否符合：" + isok, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(v.getContext(), "请输入手机号 ", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(v.getContext(), "两次输入不同相同 ", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(v.getContext(), "请输入密码 ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(v.getContext(), "请输入用户名 ", Toast.LENGTH_SHORT).show();
                }
                if (p1 && p2 && p3) {
                    Toast.makeText(v.getContext(), "用户名：" + us + "密码：" + pw1 + "手机号：" + Phone, Toast.LENGTH_SHORT).show();
                    //将注册的用户名和密码写入用户偏好
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("UserName", us);
                    editor.putString("Password", pw1);
                    editor.putString("Phone", Phone);
                    editor.commit();
                    Log.d("TTAG", sp.getString("UserName", "asdasdasd"));
                    Log.d("TTAG", sp.getString("Password", "asdasdasd"));
                    finish();
                }
            }
        });
        NO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void initview() {

        usename = (EditText) findViewById(R.id.usename);

        password1 = (EditText) findViewById(R.id.password1);
        password2 = (EditText) findViewById(R.id.password2);

        phone = (EditText) findViewById(R.id.phone);

        OK = (Button) findViewById(R.id.OK);
        NO = (Button) findViewById(R.id.NO);

    }

    public static boolean okPhone(String phone) {

//      Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\d])|(18[0,5-9]))\\d{8}$");
        Pattern p = Pattern.compile("^(13|15|14|17|18)\\d{9}$");
        Matcher m = p.matcher(phone);
        System.out.println(m.matches() + "------");
        return m.matches();
    }

}
