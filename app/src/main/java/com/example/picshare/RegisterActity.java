package com.example.picshare;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActity extends AppCompatActivity {

    private Button bt_register;
    private EditText et_username;
    private EditText et_password;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        bt_register = findViewById(R.id.bt_signup);
        et_username = findViewById(R.id.et_rusername);
        et_password = findViewById(R.id.et_rpwd);

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BmobUser user = new BmobUser();
                user.setUsername(et_username.getText().toString());
                user.setPassword(et_password.getText().toString());

                user.signUp(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e){
                        if(e==null)
                        {
                            Toast.makeText(RegisterActity.this,
                                    "注册成功",Toast.LENGTH_SHORT).show();
                        }else
                        {
                            Log.e("注册失败", "原因: ",e );
                            Toast.makeText(RegisterActity.this,
                                    "注册失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }


}
