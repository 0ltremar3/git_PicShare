package fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.picshare.R;
import com.example.picshare.ShowImageActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.PicAdapter;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import io.reactivex.annotations.Nullable;
import table.PicInfo;

public class HomeFragment extends Fragment {

    private PullRefreshLayout pullRefreshLayout;
    private ListView lvPics;
    private HashMap mHashmap;
    private List<Map<String, ?>> mapList = new ArrayList<Map<String, ?>>();
    private SimpleAdapter simpleAdapter;
//    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lvPics = getActivity().findViewById(R.id.lv_pics);
        lvPics.setScrollbarFadingEnabled(true);

        getContent();

        pullRefreshLayout = getActivity().findViewById(R.id.swipeRefreshLayout);
        // listen refresh event
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // start refresh
                getContent();
            }
        });

//        User user = BmobUser.getCurrentUser(User.class);

    }

    public void getContent() {
//        Toast.makeText(getActivity(),"homePage",Toast.LENGTH_SHORT).show();
        //只返回PicInfo表的pic这列的值
        BmobQuery<PicInfo> bmobQuery = new BmobQuery<PicInfo>();
        bmobQuery.order("-createdAt");
//        bmobQuery.addQueryKeys("owner");
        bmobQuery.findObjects(new FindListener<PicInfo>() {
            @Override
            public void done(final List<PicInfo> list, BmobException e) {
                if (e == null) {
                    //sucess
                    Toast.makeText(getActivity(),
                            "加载成功：共" + list.size() + "条动态。",
                            Toast.LENGTH_SHORT).show();

                    final PicAdapter picAdapter = new PicAdapter(getActivity(), list);
                    lvPics.setAdapter(picAdapter);

//                          刷新完成
                    pullRefreshLayout.setRefreshing(false);

                } else {
                    //failed
                    Toast.makeText(getActivity(),
                            "加载失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
