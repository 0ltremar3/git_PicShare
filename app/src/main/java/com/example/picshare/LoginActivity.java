package com.example.picshare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity {

    private EditText et_username;
    private EditText et_password;
    private CheckBox cb_rememberPwd;
    private Button bt_login;
    private TextView tvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bmob.initialize(this, "b1e48c3f44fbbee30ed2b36ddeb15dbe");

        tvSignUp = findViewById(R.id.tv_signup);
        et_username = findViewById(R.id.et_Account);
        et_password = findViewById(R.id.et_pwd);
        cb_rememberPwd = findViewById(R.id.cb_remember_pwd);
        bt_login = findViewById(R.id.bt_login);

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,RegisterActity.class);
                startActivity(intent);
            }
        });


        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BmobUser userlogin=new BmobUser();
                userlogin.setUsername(et_username.getText().toString());
                userlogin.setPassword(et_password.getText().toString());
                userlogin.login(new SaveListener<BmobUser>() {
                    @Override
                    public void done(BmobUser bmobUser, BmobException e) {
                        if(e==null){
                            Toast.makeText(LoginActivity.this,
                                    bmobUser.getUsername()+"登录成功",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.setClass(LoginActivity.this,MainActivity.class);
                            startActivity(intent);

                        }else {
                            Log.e("登录失败", "原因: ", e);
                            Toast.makeText(LoginActivity.this,
                                     "登录失败,账号或密码错误",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}

