package fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import io.reactivex.annotations.Nullable;
import table.PicInfo;
import table.User;

public class HomeFragment extends Fragment {

    private PullRefreshLayout pullRefreshLayout;
    private ListView lvPics;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_home,container,false);
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

    }

    public void getContent(){

        BmobQuery<PicInfo> bmobQuery = new BmobQuery<PicInfo>();
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(new FindListener<PicInfo>() {
                    @Override
                    public void done(final List<PicInfo> list, BmobException e) {
                        if (e == null){
                            //sucess
                            Toast.makeText(getActivity(),
                                    "加载成功：共" + list.size() + "条动态。",
                                    Toast.LENGTH_SHORT).show();

                            final PicAdapter picAdapter = new PicAdapter(getActivity(),list);
                            lvPics.setAdapter(picAdapter);
//                            picAdapter.setOnItemShareClickListener(new PicAdapter.onItemShareListener() {
//                                @Override
//                                public void onShareClick(int i) {
//                                    // TODO: 2019/6/29
//                                    ClipboardManager clipboardManager = (ClipboardManager)getActivity().
//                                            getSystemService(Context.CLIPBOARD_SERVICE);
//                                    ClipData mClipData = ClipData.newPlainText("Label", "这里是要复制的文字");
//                                    // 将ClipData内容放到系统剪贴板里。
//                                    clipboardManager.setPrimaryClip(mClipData);
//                                    Toast.makeText(getActivity(),
//                                            "已复制分享链接!",
//                                            Toast.LENGTH_SHORT).show();
//
//                                }
//                            });
//                            picAdapter.setOnItemPicClickListener(new PicAdapter.onItemPicListener() {
//                                @Override
//                                public void onPicClick(int i) {
//                                    Intent intent = getActivity().getIntent();
//                                    String imgPath = intent.getStringExtra("imagePath");
//
//                                    View imgEntryView = View.inflate(getActivity(),R.layout.dialog_photo_entry,null);
//                                    ImageView imageView = imgEntryView.findViewById(R.id.iv_dialog);
//
//                                    Dialog dialog = new Dialog(getActivity());
//
//                                    Glide.with(getActivity())
//                                            .load(imgPath).placeholder(R.drawable.bitmap)//加载未完成时显示占位图
//                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
//                                            .into(imageView);//显示的位置
//                                    dialog.setContentView(imageView);
//                                    dialog.show();
//
//                                }
//                            });

//                          刷新完成
                                pullRefreshLayout.setRefreshing(false);

                }else {
                    //failed
                    Toast.makeText(getActivity(),
                            "加载失败",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    //下载图片
    private void downloadPic(PicInfo picInfo) {
        BmobFile pic = picInfo.getPic();
        pic.download(new DownloadFileListener() {
            @Override
            public void done(String s, BmobException e) {

            }

            @Override
            public void onProgress(Integer integer, long l) {
                //设置进度条并显示
            }
        });

    }


//    private View getViewByPosition(int pos, ListView listView) {
//        final int firstListItemPosition = listView.getFirstVisiblePosition();
//        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;
//
//        if (pos < firstListItemPosition || pos > lastListItemPosition) {
//            return listView.getAdapter().getView(pos, null, listView);
//        } else {
//            final int childIndex = pos - firstListItemPosition;
//            return listView.getChildAt(childIndex);
//        }
//    }
}
