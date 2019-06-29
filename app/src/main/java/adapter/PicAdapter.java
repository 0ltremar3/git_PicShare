package adapter;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.widget.BaseAdapter;
import android.content.Context;
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

import java.util.List;

import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;
import fragments.HomeFragment;
import table.PicInfo;

/**
 * Created by awei on 2017/12/13.
 */

public class PicAdapter extends BaseAdapter {
    private LinearLayout mLinearLayout;
    private Context mContext;
    private List<PicInfo> mList;

    public PicAdapter(Context mContext, List<PicInfo> mList) {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {//判断view是否可以重载
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            mLinearLayout = (LinearLayout) inflater.inflate(R.layout.pics_item, null);
            //获取id
            viewHolder.mName = mLinearLayout.findViewById(R.id.tv_name);
            viewHolder.mPicture = mLinearLayout.findViewById(R.id.iv_picture);
            viewHolder.mlike = mLinearLayout.findViewById(R.id.iv_like);
            viewHolder.mShare = mLinearLayout.findViewById(R.id.iv_share);
            viewHolder.mTime = mLinearLayout.findViewById(R.id.tv_time);
            //设置数据
            viewHolder.mName.setText(mList.get(i).getOwner());
            viewHolder.mTime.setText(mList.get(i).getCreatedAt());
            //子控件点击事件
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
//                    Intent intent = new Intent(mContext, HomeFragment.class);
//                    intent.putExtra("imagePath",mList.get(i).getPicUrl());
//                    mOnItemSharePicListener.onShareClick(i);
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
        } else {//可以重载则直接使用原来的view
            mLinearLayout = (LinearLayout) view;
            viewHolder = (ViewHolder) mLinearLayout.getTag();
            //获取id
            viewHolder.mName = mLinearLayout.findViewById(R.id.tv_name);
            viewHolder.mPicture = mLinearLayout.findViewById(R.id.iv_picture);
            viewHolder.mlike = mLinearLayout.findViewById(R.id.iv_like);
            viewHolder.mShare = mLinearLayout.findViewById(R.id.iv_share);
            viewHolder.mTime = mLinearLayout.findViewById(R.id.tv_time);
            //设置数据
            viewHolder.mName.setText(mList.get(i).getOwner());
            viewHolder.mTime.setText(mList.get(i).getCreatedAt());
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
//                    Intent intent = new Intent(mContext, HomeFragment.class);
//                    intent.putExtra("imagePath",mList.get(i).getPicUrl());
//                    mOnItemSharePicListener.onShareClick(i);
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

    /**
     * 图片的监听接口
     */
    public interface onItemPicListener {
        void onPicClick(int i);
    }

    private onItemPicListener mOnItemPicListener;

    public void setOnItemPicClickListener(onItemPicListener mOnItemPicClickListener) {
        this.mOnItemPicListener = mOnItemPicClickListener;
    }

    //分享接口
    public interface onItemShareListener {
        void onShareClick(int i);
    }

    private onItemShareListener  mOnItemSharePicListener;

    public void setOnItemShareClickListener(onItemShareListener mOnItemSharePicListener) {
        this.mOnItemSharePicListener = mOnItemSharePicListener;
    }

    //使用viewHolder缓存数据
    static class ViewHolder {
        TextView mName;
        ImageView mPicture;
        TextView mTime;
        ImageView mlike;
        ImageView mDownload;
        ImageView mShare;
    }
}

