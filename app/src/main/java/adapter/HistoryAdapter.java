package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import table.PicInfo;

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
        PicAdapter.ViewHolder viewHolder;
        if (view == null){

        }else {

        }
        return mLinearLayout;
    }

    static class ViewHolder {
        TextView mName;
        ImageView mPicture;
        TextView mTime;
        ImageView mlike;
    }
}
