package com.example.picshare;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import table.User;

public class RegisterActity extends AppCompatActivity {

    private Button bt_register;
    private EditText et_username;
    private EditText et_password;
    private EditText et_confirm;
    private ImageView ivPwdSwitch;
    private ImageView ivconfirmSwitch;
    private Boolean bPwdSwitch = false;
    private Boolean bConfirmSwitch = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        bt_register = findViewById(R.id.bt_signup);
        et_username = findViewById(R.id.et_rusername);
        et_password = findViewById(R.id.et_rpwd);
        et_confirm = findViewById(R.id.et_confirm);

        ivPwdSwitch = findViewById(R.id.iv_pwd_switch);
        ivconfirmSwitch = findViewById(R.id.iv_confirm_switch);

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.equals(et_password.getText(),et_confirm.getText())){
                    bmobUserRegister();
                }else {
                    Toast.makeText(RegisterActity.this,
                            "两次输入密码不一致",Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivPwdSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bPwdSwitch = !bPwdSwitch;
                if (!bPwdSwitch) {
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

        ivconfirmSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bConfirmSwitch = !bConfirmSwitch;
                if (!bConfirmSwitch) {
                    ivconfirmSwitch.setImageResource(R.drawable.ic_visibility_black_24dp);
                    et_confirm.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    ivconfirmSwitch.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                    et_confirm.setInputType(
                            InputType.TYPE_TEXT_VARIATION_PASSWORD |
                                    InputType.TYPE_CLASS_TEXT);
                    et_confirm.setTypeface(Typeface.DEFAULT);
                }
            }
        });
    }
    public void bmobUserRegister(){
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
                    Intent intent = new Intent(RegisterActity.this,LoginActivity.class);
                    startActivity(intent);
                }else
                {
                    Log.e("注册失败", "原因: ",e );
                    Toast.makeText(RegisterActity.this,
                            "注册失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
