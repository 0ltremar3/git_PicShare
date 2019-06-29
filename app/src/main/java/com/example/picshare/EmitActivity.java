package com.example.picshare;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.internal.entity.IncapableCause;
import com.zhihu.matisse.internal.entity.Item;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import io.reactivex.functions.Consumer;
import method.Glide4Engine;
import table.PicInfo;
import table.User;

public class EmitActivity extends AppCompatActivity
        implements View.OnClickListener {

    private String mpicPath;
    private BmobFile bmobFile;
    private ImageView mView;
    private ProgressBar mprogress;
    public static final int PICK_PHOTO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emit);

//        Bmob.initialize(this, "b1e48c3f44fbbee30ed2b36ddeb15dbe");

        findViewById(R.id.iv_choosePic).setOnClickListener(this);
        findViewById(R.id.bt_upload).setOnClickListener(this);

        mView = findViewById(R.id.iv_pic);
        mprogress = findViewById(R.id.progress);

        initPermission();

    }

    private void initPermission()
    {
        RxPermissions rxPermissions=new RxPermissions(this);

        rxPermissions.request(
                Manifest.permission.CAMERA
                ,Manifest.permission.WRITE_EXTERNAL_STORAGE
                ,Manifest.permission.READ_EXTERNAL_STORAGE
        ).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean){
                    //申请的权限全部允许
                    Toast.makeText(EmitActivity.this, "允许了权限!", Toast.LENGTH_SHORT).show();
                }else{
                    //只要有一个权限被拒绝，就会执行
                    Toast.makeText(EmitActivity.this, "未授权权限，部分功能不能使用", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.iv_choosePic:
                selectPic();
                break;
            case R.id.bt_upload:
                uploadPic(mpicPath);
//                uploadPic("sdcard/tmp.jpg");
                mprogress.getProgress();
//                Toast.makeText(this, mpicPath, Toast.LENGTH_SHORT).show();
                break;
        }
    }
//
    private final int REQUEST_CODE_CHOOSE_PHOTO_ALBUM = 1;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_CHOOSE_PHOTO_ALBUM && resultCode == RESULT_OK)
        {
            //图片路径 同样视频地址也是这个 根据requestCode
            List<Uri> pathList = Matisse.obtainResult(data);
            for (Uri _Uri : pathList)
            {
                Glide.with(this).load(_Uri).into(mView);//显示选择的图片
                System.out.println(_Uri.getPath());
                mpicPath = getRealPathFromURI(EmitActivity.this,_Uri);

            }
        }
    }
//  uri转文件路径
    public static String getRealPathFromURI(Context context, Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI,
                new String[]{MediaStore.Images.ImageColumns.DATA},//
                null, null, null);
        if (cursor == null) result = contentURI.getPath();
        else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(index);
            cursor.close();
        }
        return result;
    }

    void selectPic()
    {
        Matisse.from(this)
                .choose(MimeType.ofImage(), false)
                .capture(true)  // 使用相机，和 captureStrategy 一起使用
                .captureStrategy(new CaptureStrategy(true, "com.example.picshare"))
//        R.style.Matisse_Zhihu (light mode)
//        R.style.Matisse_Dracula (dark mode)
                .theme(R.style.Matisse_Dracula)
                .countable(true)
                .maxSelectable(1)
                .addFilter(new Filter() {
                    @Override
                    protected Set<MimeType> constraintTypes() {
                        return new HashSet<MimeType>() {{
                            add(MimeType.PNG);
                        }};
                    }

                    @Override
                    public IncapableCause filter(Context context, Item item) {
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(item.getContentUri());
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inJustDecodeBounds = true;
                            BitmapFactory.decodeStream(inputStream, null, options);
                            int width = options.outWidth;
                            int height = options.outHeight;

//                            if (width >= 500)
//                                return new IncapableCause("宽度超过500px");

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
//                .gridExpectedSize((int) getResources().getDimension(R.dimen.imageSelectDimen))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.87f)
                .imageEngine(new Glide4Engine())
                .forResult(REQUEST_CODE_CHOOSE_PHOTO_ALBUM);
    }

    void uploadPic(String Picpath){
        //Bmob上传文件到数据库
        bmobFile = new BmobFile(new File(Picpath));
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {

                if(e==null){//上传成功
//                    bmobFile.getFileUrl()--返回的上传文件的完整地址
                    Toast.makeText(EmitActivity.this,
                            "上传文件成功:" + bmobFile.getFileUrl(),
                            Toast.LENGTH_SHORT).show();
                    PicInfo picInfo = new PicInfo();
                    picInfo.setPic(bmobFile);
                    picInfo.setPath(mpicPath);
                    picInfo.setPicUrl(bmobFile.getFileUrl());
                    picInfo.setOwner(BmobUser.getCurrentUser(User.class).getUsername());
                    picInfo.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                        }
                    });


                }else{
                    Toast.makeText(EmitActivity.this,
                            "上传文件失败：" + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
                mprogress.setProgress(value);
            }
        });
    }


}
