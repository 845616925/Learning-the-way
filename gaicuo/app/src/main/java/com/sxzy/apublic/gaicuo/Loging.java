package com.sxzy.apublic.gaicuo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/6/12.
 */

public class Loging extends Activity {
    TextView textView;
    EditText editText1, editText2;
    CheckBox checkBox1, checkBox2;
    Button button1, button2;
    SharedPreferences sp, sp2;
    String userNameValue, passwordValue;
    String userName;
    String password;
    boolean choseRemember;
    boolean choseAutoLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.one);
        initview();

        sp = getSharedPreferences("Loging", MODE_PRIVATE);
        sp2 = getSharedPreferences("Password", MODE_PRIVATE);

        userName = sp.getString("UserName", "admin");
        password = sp.getString("Password", "admin");
        choseRemember = sp.getBoolean("remember", false);
        choseAutoLogin = sp.getBoolean("autologin", false);

        if (choseRemember) {//判断上次是否点击记住密码
            editText1.setText(userName);
            editText2.setText(password);
            checkBox1.setChecked(true);
        }

        if (choseAutoLogin) {//判断上次是否点击自动登录
            checkBox2.setChecked(true);
            String userName = editText1.getText().toString();
            String password = editText2.getText().toString();
            if (userName.equals(sp2.getString("UserName", "admin")) && password.equals(sp2.getString("Password", "admin"))) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5 * 1000);
                            if (checkBox2.isChecked()) {
                                Intent intent = new Intent(Loging.this, showmain.class);
                                startActivity(intent);
                                finish();
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();


            }
        }

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userNameValue = editText1.getText().toString();
                passwordValue = editText2.getText().toString();
                SharedPreferences.Editor editor = sp.edit();
                if (userNameValue.equals(sp2.getString("UserName", "admin")) && passwordValue.equals(sp2.getString("Password", "admin"))) {
                    Toast.makeText(Loging.this, "登录成功", Toast.LENGTH_SHORT).show();
                    editor.putString("UserName", userNameValue);
                    editor.putString("Password", passwordValue);

                    //记住密码
                    if (checkBox1.isChecked()) {
                        editor.putBoolean("remember", true);
                    } else {
                        editor.putBoolean("remember", false);
                    }

                    //是否自动登录
                    if (checkBox2.isChecked()) {
                        editor.putBoolean("autologin", true);
                    } else {
                        editor.putBoolean("autologin", false);
                    }
                    editor.commit();

                    Intent intent = new Intent(Loging.this, showmain.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Loging.this, "用户名或密码错误，请重新登录!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Loging.this, Register.class);
                startActivity(intent);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {

            private Button button2;
            private Button button;
            private EditText editText;
            private boolean okport;

            @Override
            public void onClick(View v) {
                //得到布局
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.websetting, null);
                //创建dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                final AlertDialog dialog = builder.create();
                dialog.setView(view);
                dialog.show();
                editText = (EditText) view.findViewById(R.id.port);
                button = (Button) view.findViewById(R.id.button);
                button2 = (Button) view.findViewById(R.id.button2);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String port = editText.getText().toString();
                        okport = okport(port);
                        if (okport) {
                            Toast.makeText(v.getContext(), "端口号正确", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(v.getContext(), "端口号错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    void initview() {
        editText1 = (EditText) findViewById(R.id.oneUseName);
        editText2 = (EditText) findViewById(R.id.onePassword);
        checkBox1 = (CheckBox) findViewById(R.id.oneRememberPassword);
        checkBox2 = (CheckBox) findViewById(R.id.oneAutoLanding);
        button1 = (Button) findViewById(R.id.oneLanding);
        button2 = (Button) findViewById(R.id.oneRegister);
        textView = (TextView) findViewById(R.id.webSetting);
    }

    public static boolean okport(String port) {

        Pattern p = Pattern.compile("^([1-9][0-9]{0,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]{1}|6553[0-5])$");
        Matcher m = p.matcher(port);
        System.out.println(m.matches() + "------");
        return m.matches();
    }
}
