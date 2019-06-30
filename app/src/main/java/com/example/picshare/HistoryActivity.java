package com.example.picshare;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;

import java.util.List;

import adapter.PicAdapter;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import table.PicInfo;
import table.User;

public class HistoryActivity extends AppCompatActivity {

    private ListView lv_history;
    private PullRefreshLayout pullRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        lv_history = findViewById(R.id.lv_history);
        lv_history.setScrollbarFadingEnabled(true);

        getContent();

        pullRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        // listen refresh event
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // start refresh

                getContent();
            }
        });


    }

    public void getContent(){
//        Toast.makeText(getActivity(),"homePage",Toast.LENGTH_SHORT).show();
        //只返回PicInfo表的pic这列的值
        BmobQuery<PicInfo> bmobQuery = new BmobQuery<PicInfo>();
        bmobQuery.order("-createdAt");
        String username = BmobUser.getCurrentUser(User.class).getUsername();
        bmobQuery.addWhereEqualTo("owner", username);
        bmobQuery.findObjects(new FindListener<PicInfo>() {
            @Override
            public void done(final List<PicInfo> list, BmobException e) {
                if (e == null){
                    //sucess
                    Toast.makeText(HistoryActivity.this,
                            "加载成功：共" + list.size() + "条动态。",
                            Toast.LENGTH_SHORT).show();

                    final PicAdapter picAdapter = new PicAdapter(HistoryActivity.this,list);
                    lv_history.setAdapter(picAdapter);
                    pullRefreshLayout.setRefreshing(false);

                }else {
                    //failed
                    Toast.makeText(HistoryActivity.this,
                            "加载失败",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
