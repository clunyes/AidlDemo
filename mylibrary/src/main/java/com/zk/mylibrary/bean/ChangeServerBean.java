package com.zk.mylibrary.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author kang.zhao
 * @ClassName: ChangeServerBean
 * @date 2020-07-14 14:41
 * @Description:
 */
public class ChangeServerBean implements Parcelable {
    private String content;

    public ChangeServerBean() {
    }


    protected ChangeServerBean(Parcel in) {
        content = in.readString();
    }

    public static final Creator<ChangeServerBean> CREATOR = new Creator<ChangeServerBean>() {
        @Override
        public ChangeServerBean createFromParcel(Parcel in) {
            return new ChangeServerBean(in);
        }

        @Override
        public ChangeServerBean[] newArray(int size) {
            return new ChangeServerBean[size];
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
