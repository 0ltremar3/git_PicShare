package com.example.picshare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.SaveListener;
import table.User;

public class LoginActivity extends AppCompatActivity {

    private EditText et_username;
    private EditText et_password;
    private CheckBox cb_rememberPwd;
    private Button bt_login;
    private TextView tvSignUp;
    private Boolean bPwdSwitch = false;
    public BmobUser user;
    private ImageView ivPwdSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bmob.initialize(this, "b1e48c3f44fbbee30ed2b36ddeb15dbe");
//        getUser();
        if (BmobUser.isLogin()){
            Intent intent = new Intent(LoginActivity.this,
                    GuideActivity.class);
            startActivity(intent);
            finish();
        }

        tvSignUp = findViewById(R.id.tv_signup);
        et_username = findViewById(R.id.et_Account);
        et_password = findViewById(R.id.et_pwd);
        cb_rememberPwd = findViewById(R.id.cb_remember_pwd);
        bt_login = findViewById(R.id.bt_login);
        ivPwdSwitch = findViewById(R.id.iv_pwd_switch);

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisterActity.class);
                startActivity(intent);
            }
        });

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bmobUserLogin();
            }
        });

        //      密码明文切换
        ivPwdSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bPwdSwitch = !bPwdSwitch;
                if (bPwdSwitch) {
                    ivPwdSwitch.setImageResource(R.drawable.ic_visibility_black_24dp);
                    et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    ivPwdSwitch.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                    et_password.setInputType(
                            InputType.TYPE_TEXT_VARIATION_PASSWORD |
                                    InputType.TYPE_CLASS_TEXT);
                    et_password.setTypeface(Typeface.DEFAULT);
                }
            }
        });

    }

    public void bmobUserLogin() {
        final BmobUser userlogin = new BmobUser();
        userlogin.setUsername(et_username.getText().toString());
        userlogin.setPassword(et_password.getText().toString());
        userlogin.login(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
//                    fetchUserInfo(getCurrentFocus());
                    Toast.makeText(LoginActivity.this,
                            bmobUser.getUsername() + "登录成功", Toast.LENGTH_SHORT).show();
//                    getUserCache();
                    if (cb_rememberPwd.isChecked()){
                        User user = BmobUser.getCurrentUser(User.class);
                        String username = (String) BmobUser.getObjectByKey("username");
                        String userpwd = (String) BmobUser.getObjectByKey("password");
//                        restoreInfo(username,userpwd);
                    }
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, GuideActivity.class);
                    startActivity(intent);

                } else {
                    Log.e("登录失败", "原因: ", e);
                    Toast.makeText(LoginActivity.this,
                            "登录失败,账号或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void restoreInfo(String username,String userpwd){
        et_username.setText(username);
        et_password.setText(userpwd);
        cb_rememberPwd.setChecked(false);
    }

//    public void getUserCache(){
//        if (BmobUser.isLogin()) {
//            User user = BmobUser.getCurrentUser(User.class);
//            String username = (String) BmobUser.getObjectByKey("username");
//            String userpwd = (String) BmobUser.getObjectByKey("password");
//            Toast.makeText(LoginActivity.this,
//                    username + "登录成功", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(LoginActivity.this,
//                    "尚未登录，请先登录", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void fetchUserInfo(final View view) {
//        BmobUser.fetchUserInfo(new FetchUserInfoListener<BmobUser>() {
//            @Override
//            public void done(BmobUser user, BmobException e) {
//                if (e == null) {
//                    final User myUser = BmobUser.getCurrentUser(User.class);
//                    Toast.makeText(LoginActivity.this,
//                            "更新用户本地缓存信息成功：" + myUser.getUsername(),
//                            Toast.LENGTH_SHORT).show();
//
//                } else {
//                    Log.e("error",e.getMessage());
//                    Toast.makeText(LoginActivity.this,
//                            "更新用户本地缓存信息失败",
//                            Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }

}