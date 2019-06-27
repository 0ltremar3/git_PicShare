package table;

import java.security.acl.Owner;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class PicInfo extends BmobObject {
    String id;
    String owner;
    String path;
    BmobFile pic;

    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getPath(){
        return path;
    }
    public void setPath(String path){
        this.path = path;
    }

    public BmobFile getPic(){return pic;}
    public void setPic(BmobFile pic){this.pic = pic;}
}
