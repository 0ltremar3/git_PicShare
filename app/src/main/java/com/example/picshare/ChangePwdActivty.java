package com.example.picshare;

//import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;


public class ChangePwdActivty extends AppCompatActivity implements View.OnClickListener {

    EditText now_pass;
    EditText new_pass;

//    TextInputLayout textInputLayout;

    Button next_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

//        textInputLayout = findViewById(R.id.now_pass_layout);
        now_pass = findViewById(R.id.EditView1);
        new_pass = findViewById(R.id.EditView2);
        next_button = findViewById(R.id.next_button);

        next_button.setOnClickListener(this);
        //Toolbar toolbar = findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        setTitle("修改密码");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        String pass = now_pass.getText().toString();
        String pass_ = new_pass.getText().toString();
        BmobUser.updateCurrentUserPassword(pass, pass_, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
                    finish();
                }else {Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),"旧密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
