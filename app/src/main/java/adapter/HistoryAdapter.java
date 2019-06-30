package adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.picshare.R;
import com.example.picshare.ShowImageActivity;
import com.ldoublem.thumbUplib.ThumbUpView;

import java.util.List;

import cn.bmob.v3.BmobUser;
import method.BmobUtils;
import table.PicInfo;
import table.User;

public class HistoryAdapter extends BaseAdapter {
    private LinearLayout mLinearLayout;
    private Context mContext;
    private List<PicInfo> mList;
    public HistoryAdapter(Context mContext, List<PicInfo> mList){
        this.mContext = mContext;
        this.mList = mList;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup){
        final ViewHolder viewHolder;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            mLinearLayout = (LinearLayout) inflater.inflate(R.layout.history_item, null);

            viewHolder.mName = mLinearLayout.findViewById(R.id.tv_name);
            viewHolder.mTime = mLinearLayout.findViewById(R.id.tv_time);
            viewHolder.mPicture = mLinearLayout.findViewById(R.id.iv_picture);
            viewHolder.mlike = mLinearLayout.findViewById(R.id.iv_like);
            viewHolder.mShare = mLinearLayout.findViewById(R.id.iv_share);

            viewHolder.mlike.setOnThumbUp(new ThumbUpView.OnThumbUp() {
                @Override
                public void like(boolean like) {
                    String username = BmobUser.getCurrentUser(User.class).getUsername();
                    String picId = mList.get(i).getObjectId();
                    BmobUtils.like(username,picId);//用户xx赞了图xx

                }
            });
            viewHolder.mPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//传递图片地址
                    //在HomeFragment中的setItemPic...方法里重写这个函数,实现图片点击的逻辑
//                    mOnItemPicListener.onPicClick(i);
                    Intent intent = new Intent(mContext, ShowImageActivity.class);
                    intent.putExtra("imagePath",mList.get(i).getPicUrl());
                    intent.putExtra("picFile",mList.get(i).getObjectId());
                    mContext.startActivity(intent);

                }
            });
            viewHolder.mShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentShare = new Intent();
                    intentShare.setAction(Intent.ACTION_SEND_MULTIPLE);
                    intentShare.putExtra(Intent.EXTRA_STREAM, Uri.parse(mList.get(i).getPicUrl()));
                    intentShare.setType("image/*");
                    //打开分享
                    mContext.startActivity(Intent.createChooser(intentShare, "分享到..."));
                }
            });
            //使用glide加载图片
            Glide.with(mContext)
                    .load(mList.get(i).getPicUrl()) //加载地址
                    .placeholder(R.drawable.bitmap)//加载未完成时显示占位图
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(viewHolder.mPicture);//显示的位置
            //标记当前view

            mLinearLayout.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) mLinearLayout.getTag();
            viewHolder.mName = mLinearLayout.findViewById(R.id.tv_name);
            viewHolder.mTime = mLinearLayout.findViewById(R.id.tv_time);
            viewHolder.mPicture = mLinearLayout.findViewById(R.id.iv_picture);
            viewHolder.mlike = mLinearLayout.findViewById(R.id.iv_like);
            viewHolder.mShare = mLinearLayout.findViewById(R.id.iv_share);

            viewHolder.mlike.setOnThumbUp(new ThumbUpView.OnThumbUp() {
                @Override
                public void like(boolean like) {
//                    todo



                }
            });
            //看图
            viewHolder.mPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //在HomeFragment中的setItemPic...方法里重写这个函数,实现图片点击的逻辑
//                    mOnItemPicListener.onPicClick(i);
                    Intent intent = new Intent(mContext,ShowImageActivity.class);
                    intent.putExtra("imagePath",mList.get(i).getPicUrl());
                    intent.putExtra("picFile",mList.get(i).getObjectId());
                    mContext.startActivity(intent);

                }
            });
            viewHolder.mShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intentShare = new Intent();
                    intentShare.setAction(Intent.ACTION_SEND_MULTIPLE);
                    intentShare.putExtra(Intent.EXTRA_STREAM,Uri.parse(mList.get(i).getPicUrl()));
                    intentShare.setType("image/*");
                    //打开分享
                    mContext.startActivity(Intent.createChooser(intentShare, "分享到..."));
                }
            });

            //使用glide加载图片
            Glide.with(mContext)
                    .load(mList.get(i).getPicUrl()) //加载地址
                    .placeholder(R.drawable.bitmap)//加载未完成时显示占位图
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(viewHolder.mPicture);//显示的位置
        }
        return mLinearLayout;
    }

    static class ViewHolder {
        TextView mName;
        ImageView mPicture;
        TextView mTime;
        ThumbUpView mlike;
        ImageView mShare;
    }
}
