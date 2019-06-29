package fragments;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.picshare.R;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import io.reactivex.annotations.Nullable;
import table.PicInfo;

public class UserFragment extends Fragment {
    private ImageView iv_avater;
    private TextView textView;
    private Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_user,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textView = getActivity().findViewById(R.id.tv_user);
        button = getActivity().findViewById(R.id.bt_user);
        button.setText("下载");
        iv_avater = getActivity().findViewById(R.id.iv_avater);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                BmobQuery<PicInfo> picInfoBmobQuery = new BmobQuery<>();
//                picInfoBmobQuery.findObjects(new FindListener<PicInfo>() {
//                    @Override
//                    public void done(List<PicInfo> picInfoList, BmobException e) {
//                        if (e == null){
//                            Glide.with(iv_avater.getContext()).
//                                    load(picInfoList.get(0).getPicUrl()).into(iv_avater);
////                            downloadPic(picInfo);
//                        }else {
//                            Log.e("找不到文件","原因：" + e);
//                        }
//                    }
//                });

//
                Toast.makeText(getActivity(),"userPage",Toast.LENGTH_SHORT).show();
            }
        });
    }


}
