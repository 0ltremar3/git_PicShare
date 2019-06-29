package com.example.picshare;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import fragments.HomeFragment;
import fragments.UserFragment;
import table.User;

public class GuideActivity extends AppCompatActivity {

    private  HomeFragment homeFragment;
    private UserFragment userFragment;
    private Fragment[] fragments;
    private int lastfragment;
    private BottomNavigationView navView;
    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        Bmob.initialize(this, "b1e48c3f44fbbee30ed2b36ddeb15dbe");
        initFragment();

    }

    private void initFragment(){
        homeFragment = new HomeFragment();
        userFragment = new UserFragment();
        fragments = new Fragment[]{homeFragment,userFragment};
        lastfragment = 0;
        navView = findViewById(R.id.nav_view);

        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().
                replace(R.id.mainview,homeFragment).show(homeFragment).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
                    if(lastfragment != 0)
                    {
                        switchFragment(lastfragment,0);
                        lastfragment = 0;
                    }
                    return true;
                case R.id.navigation_emit:{
//                  mTextMessage.setText(R.string.title_emit);
                    Intent intent = new Intent();
                    intent.setClass(GuideActivity.this,EmitActivity.class);
                    startActivity(intent);
                    return true;
                }
                case R.id.navigation_user:
//                    mTextMessage.setText(R.string.title_user);
                    if(lastfragment != 1)
                    {
                        switchFragment(lastfragment,1);
                        lastfragment = 1;

                    }
                    return true;
            }
            return false;
        }
    };

    private void switchFragment(int lastfragment,int index) {
       FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastfragment]);//隐藏上个Fragment
        if (fragments[index].isAdded() == false) {

            transaction.add(R.id.mainview, fragments[index]);

        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }

}
