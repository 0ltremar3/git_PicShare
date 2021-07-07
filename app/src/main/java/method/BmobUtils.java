package method;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import table.Like;
import table.PicInfo;

public class BmobUtils {
    public static  void like(String username, String setId)
    {
        final Like like = new Like();
        like.setSetId(setId);
        like.setUsername(username);
        like.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                BmobQuery<Like> likeBmobQuery = new BmobQuery<>();
                likeBmobQuery.findObjects(new FindListener<Like>() {
                    @Override
                    public void done(List<Like> list, BmobException e) {
                        if (list.size() >0) {
                            return; //若已有数据，
                        }else {
                            like.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {

                                }
                            });
                        }
                    }
                });
            }
        });


    }
    public static void unLike(String username, String setId)
    {
        final Like like = new Like();
        like.setSetId(setId);
        like.setUsername(username);
        BmobQuery<Like> likeBmobQuery = new BmobQuery<>();
        likeBmobQuery.addWhereEqualTo("username",like.getUsername());
        likeBmobQuery.addWhereEqualTo("setId",like.getSetId());
        likeBmobQuery.findObjects(new FindListener<Like>() {
            @Override
            public void done(List<Like> list, BmobException e) {
                if (list.size() >0) {
                    list.get(0).delete(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {

                        }
                    });
                }
            }
        });
    }

    static public void deletePicInfo(String setId)
    {
        PicInfo picInfo = new PicInfo();
        picInfo.setObjectId(setId);
        picInfo.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {

            }
        });
    }

}
