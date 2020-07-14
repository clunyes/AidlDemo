package com.zk.mylibrary.server2ui;

import android.os.RemoteException;

import com.zk.mylibrary.IServerConnect;
import com.zk.mylibrary.bean.ChangeUiBean;

/**
 * @author kang.zhao
 * @ClassName: ServerCallback
 * @date 2020-07-14 15:58
 * @Description:
 */
public class ServerCallbackImpl extends IServerConnect.Stub {
    private IServerCallback iServerCallback;

    public ServerCallbackImpl(IServerCallback callback) {
        iServerCallback = callback;
    }


    @Override
    public void serverChangeUI(ChangeUiBean changeUiBean) throws RemoteException {
        if (iServerCallback != null) {
            iServerCallback.serverChangeUI(changeUiBean);
        }
    }
}
