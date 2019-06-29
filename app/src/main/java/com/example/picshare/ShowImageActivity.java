package com.example.picshare;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import io.reactivex.functions.Consumer;
import table.PicInfo;

public class ShowImageActivity extends AppCompatActivity {

    private String imgId;
    private String imgPath;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_photo_entry);

        ImageView img = findViewById(R.id.iv_dialog);
        Intent intent = getIntent();
        imgPath = intent.getStringExtra("imagePath");
        imgId = intent.getStringExtra("picFile");
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
//        Toast.makeText(this,imgPath,Toast.LENGTH_SHORT).show();
        Glide.with(ShowImageActivity.this)
                .load(imgPath).placeholder(R.drawable.bitmap)//加载未完成时显示占位图
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(img);//显示的位置
        img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ShowImageActivity.this);
                builder.setItems(new String[]{"保存", "取消"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:{
                                //点击了保存
                                progressBar.setVisibility(View.VISIBLE);
                                downLoadPic();
//                                Toast.makeText(ShowImageActivity.this,
//                                        imgId,Toast.LENGTH_SHORT).show();
                                break;

                            }
                            case 1:{
                                builder.setCancelable(true);
                            }
                        }
                    }
                }).create();

                //显示对话框
                builder.show();
                return true;
            }
        });
    }

    public void downLoadPic(){
        BmobQuery<PicInfo> bmobQuery = new BmobQuery<PicInfo>();
//        final File externalStoragePublicDirectory =
//                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        bmobQuery.addWhereEqualTo("objectId", imgId); //键值对
        bmobQuery.findObjects(new FindListener<PicInfo>() {
            @Override
            public void done(List<PicInfo> object, BmobException e) {
                if (e == null) {
                    for (PicInfo picInfo : object) {
                        BmobFile bmobfile = picInfo.getPic();
                        if (bmobfile != null){
                            bmobfile.download(new DownloadFileListener() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e == null){
                                        //下载成功
                                        progressBar.setVisibility(View.GONE);
                                        AlertDialog.Builder alterDialog =
                                                new AlertDialog.Builder(ShowImageActivity.this);
                                        alterDialog.setMessage("下载成功");
                                        alterDialog.show();
                                        alterDialog.setCancelable(true);
                                    }else {
                                        Toast.makeText(ShowImageActivity.this,
                                                "下载失败",Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onProgress(Integer integer, long l) {
                                    progressBar.setProgress(integer);
                                }
                            });
                        }else {
                            Toast.makeText(ShowImageActivity.this,
                                    "文件不存在",Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(ShowImageActivity.this,
                            "查询失败",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

}
