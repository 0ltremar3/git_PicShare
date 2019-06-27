package com.example.picshare;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import cn.bmob.v3.BmobUser;
import table.User;

public class GuideActivity extends AppCompatActivity {
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_emit:{
//                  mTextMessage.setText(R.string.title_emit);
                    Intent intent = new Intent();
                    intent.setClass(GuideActivity.this,EmitActivity.class);
                    startActivity(intent);
                    return true;
                }
                case R.id.navigation_user:
                    mTextMessage.setText(R.string.title_user);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        if (BmobUser.isLogin()) {
//            User user = BmobUser.getCurrentUser(User.class);
//            Snackbar.make(view, "已经登录：" + user.getUsername(), Snackbar.LENGTH_LONG).show();
//        } else {
//            Snackbar.make(view, "尚未登录", Snackbar.LENGTH_LONG).show();
//        }
    }

}
