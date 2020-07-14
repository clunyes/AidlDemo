package com.zk.mylibrary.ui2server;

import android.content.Context;

import com.zk.mylibrary.bean.ChangeServerBean;
import com.zk.mylibrary.bean.ServerDetailBean;
import com.zk.mylibrary.server2ui.IServerCallback;

/**
 * @author kang.zhao
 * @ClassName: IUIConnectApi
 * @date 2020-07-14 15:40
 * @Description:
 */
public class IUIConnectApi {
    private Context mContext;
    private IUIConnectImpl iuiConnectImpl;
    private static IUIConnectApi mInstance = null;

    /**
     * @param mContext
     * @return null if service is not available.
     */
    public static IUIConnectApi getInstance(Context mContext) {
        if (mInstance == null) {
            synchronized (IUIConnectImpl.class) {
                if (mInstance == null) {
                    try {
                        mInstance = new IUIConnectApi(mContext);
                    } catch (Exception e) {
                        mInstance = null;
                    }
                }
            }
        }
        return mInstance;
    }

    private IUIConnectApi(Context mContext) {
        this.mContext = mContext;
        iuiConnectImpl = new IUIConnectImpl(mContext);
    }

    public void init(ConnectCallBack callBack) {
        iuiConnectImpl.init(callBack);
    }

    public void registerCallback(IServerCallback callback2UI) {
        iuiConnectImpl.registerCallback(callback2UI);
    }

    public void unregisterCallback(IServerCallback callback2UI) {
        iuiConnectImpl.unregisterCallback(callback2UI);
    }

    public void changeServer(ChangeServerBean changeServerBean) {
        iuiConnectImpl.changeServer(changeServerBean);
    }

    public ServerDetailBean getServerContent() {
        return iuiConnectImpl.getServerContent();
    }
}
