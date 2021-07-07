package table;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class User extends BmobUser {
    private String nickName;//用户昵称
    private BmobFile avatar;//头像

    public String getnickName(){
        return nickName;
    }

    public void setNickName(){
        this.nickName = nickName;
    }

    public BmobFile getAvatar() {
        return avatar;

    }

    public User setAvatar(BmobFile avatar) {
        this.avatar = avatar;
        return this;
    }
}
