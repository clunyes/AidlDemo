package com.zk.lester;

import android.os.IBinder;
import android.os.RemoteException;

import com.zk.mylibrary.IServerConnect;
import com.zk.mylibrary.IUIConnect;
import com.zk.mylibrary.bean.ChangeServerBean;
import com.zk.mylibrary.bean.ChangeUiBean;
import com.zk.mylibrary.bean.ServerDetailBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kang.zhao
 * @ClassName: ServerImpl
 * @date 2020-07-14 17:12
 * @Description:
 */
public class ServerImpl extends IUIConnect.Stub {
    private List<IServerConnect> connectList = new ArrayList<>();
    private static ServerImpl sInstance;
    private LesterService service;

    public static ServerImpl getInstance(LesterService service) {
        if (null == sInstance) {
            synchronized (ServerImpl.class) {
                if (null == sInstance) {
                    sInstance = new ServerImpl(service);
                }
            }
        }
        return sInstance;
    }

    private ServerImpl(LesterService service) {
        this.service = service;
    }

    @Override
    public void registerCallback(IServerConnect connect) throws RemoteException {
        IBinder b = connect.asBinder();
        if (!connectList.contains(connect)) {
            b.linkToDeath(new DeathRecipient(connect), 0);
            connectList.add(connect);
        }
    }

    @Override
    public void unregisterCallback(IServerConnect callback) throws RemoteException {
        connectList.remove(callback);
    }

    @Override
    public void changeServer(ChangeServerBean changeServerBean) throws RemoteException {
        service.changeTask(changeServerBean);
    }

    @Override
    public ServerDetailBean getServerContent() throws RemoteException {
//        return activity.getTask();
        ServerDetailBean serverDetailBean = new ServerDetailBean();
        serverDetailBean.setContent(service.getServerContent());
        return serverDetailBean;
    }


    public void changeUi(ChangeUiBean changeUiBean) {
        for (IServerConnect connect : connectList) {
            try {
                connect.serverChangeUI(changeUiBean);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 服务端监听客户端是否died
     */
    private class DeathRecipient implements IBinder.DeathRecipient {
        private IServerConnect mConnect;

        DeathRecipient(IServerConnect connect) {
            mConnect = connect;
        }

        @Override
        public void binderDied() {
            try {
                if (ServerImpl.this.connectList.contains(mConnect)) {
                    ServerImpl.this.connectList.remove(mConnect);
                }
                mConnect = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
