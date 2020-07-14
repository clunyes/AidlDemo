package com.zk.mylibrary.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author kang.zhao
 * @ClassName: ServerDetailBean
 * @date 2020-07-14 16:19
 * @Description:
 */
public class ServerDetailBean implements Parcelable {

    private String content;

    public ServerDetailBean() {
    }

    protected ServerDetailBean(Parcel in) {
        content = in.readString();
    }

    public static final Creator<ServerDetailBean> CREATOR = new Creator<ServerDetailBean>() {
        @Override
        public ServerDetailBean createFromParcel(Parcel in) {
            return new ServerDetailBean(in);
        }

        @Override
        public ServerDetailBean[] newArray(int size) {
            return new ServerDetailBean[size];
        }
    };

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
    }

    public void readFromParcel(Parcel in) {
        content = in.readString();
    }
}
