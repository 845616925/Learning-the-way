package com.sxzy.apublic.gaicuo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.EditText;

public class Flash extends Activity {

    SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flash);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        editor = sp.edit();

        boolean isshow = sp.getBoolean("isshow", true);//获取sp中isshow的值
        if (isshow) {//判断是否是第一次安装程序
            new CountDownTimer(1000, 1000) {//创建倒计时器
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    Intent intent = new Intent(Flash.this, Loging.class);//界面跳转
                    startActivity(intent);
                    editor.putBoolean("isshow", false);//写入sp中的isshow
                    editor.commit();
                    finish();//结束
                }
            }.start();
        } else {
            Intent intent = new Intent(Flash.this, Loging.class);
            startActivity(intent);
            finish();
        }

    }


}
