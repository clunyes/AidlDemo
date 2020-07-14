package com.zk.mylibrary.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author kang.zhao
 * @ClassName: ChangeUiBean
 * @date 2020-07-14 14:42
 * @Description:
 */
public class ChangeUiBean implements Parcelable {
    private String content;

    public ChangeUiBean() {
    }

    protected ChangeUiBean(Parcel in) {
        content = in.readString();
    }

    public static final Creator<ChangeUiBean> CREATOR = new Creator<ChangeUiBean>() {
        @Override
        public ChangeUiBean createFromParcel(Parcel in) {
            return new ChangeUiBean(in);
        }

        @Override
        public ChangeUiBean[] newArray(int size) {
            return new ChangeUiBean[size];
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
