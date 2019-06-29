package fragments;

import android.content.Intent;
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
import com.example.picshare.ChangePwdActivty;
import com.example.picshare.R;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import io.reactivex.annotations.Nullable;
import table.PicInfo;
import table.User;

public class UserFragment extends Fragment {
    private TextView tv_id;
    private ImageView iv_avater;
    private Button bt_history;
    private Button bt_change;
    private Button bt_exit;

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

        iv_avater = getActivity().findViewById(R.id.iv_head);
        bt_change = getActivity().findViewById(R.id.bt_change);
        tv_id = getActivity().findViewById(R.id.tv_nickName);

        BmobUser bmobUser = BmobUser.getCurrentUser(User.class);
        String username = (String) BmobUser.getObjectByKey("username");
        String userpwd = (String) BmobUser.getObjectByKey("password");

        Toast.makeText(getActivity(),
                username,Toast.LENGTH_SHORT).show();

        tv_id.setText(username);

        bt_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePwdActivty.class);
                startActivity(intent);
            }
        });

    }

}
