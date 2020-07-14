package com.zk.mylibrary.ui2server;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.zk.mylibrary.IServerConnect;
import com.zk.mylibrary.IUIConnect;
import com.zk.mylibrary.bean.ChangeServerBean;
import com.zk.mylibrary.bean.ServerDetailBean;
import com.zk.mylibrary.server2ui.IServerCallback;
import com.zk.mylibrary.server2ui.ServerCallbackImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kang.zhao
 * @ClassName: IUIConnectImpl
 * @date 2020-07-14 14:57
 * @Description:
 */
public class IUIConnectImpl {

    private Context mContext;
    private IUIConnect iUIConnect;
    private IBinder mServiceBinder;
    private ConnectCallBack connectCallBack;
    private final Map<String, ServerCallbackImpl> callbackMap = new HashMap<String, ServerCallbackImpl>();


    private static final int MSG_RECONNECT = 101;
    private static final String SERVICE_PACKAGE = "com.zk.lester";
    private static final String SERVICE_CLASS = "com.zk.lester.LesterService";


    public IUIConnectImpl(Context mContext) {
        this.mContext = mContext;
    }

    public void init(ConnectCallBack callBack) {
        connectCallBack = callBack;
        bindRemoteService();
    }

    /**
     * 绑定服务
     */
    private void bindRemoteService() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(SERVICE_PACKAGE, SERVICE_CLASS));
        mContext.bindService(intent, mServiceConnection, Service.BIND_AUTO_CREATE);
        requestReconnect();
    }

    /**
     * 重连
     */
    private void requestReconnect() {
        LogUtils.d("requestReconnect");
        Message msg = mHandler.obtainMessage();
        msg.what = MSG_RECONNECT;
        mHandler.sendMessageDelayed(msg, 5000);
    }


    /**
     * 服务链接状态
     */
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iUIConnect = IUIConnect.Stub.asInterface(service);
            mServiceBinder = service;
            connectCallBack.onAPIReady(true);
            try {
                mServiceBinder.linkToDeath(mDeathRecipient, 1);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iUIConnect = null;
            requestReconnect();
            connectCallBack.onAPIReady(false);
        }

        @Override
        public void onBindingDied(ComponentName name) {
            iUIConnect = null;
            mContext.unbindService(mServiceConnection);
            requestReconnect();
        }
    };

    /**
     * 死亡监听
     */
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (mServiceBinder != null) {
                mServiceBinder.unlinkToDeath(mDeathRecipient, 1);
            }
            iUIConnect = null;
            mServiceBinder = null;
            connectCallBack.onAPIReady(false);
            requestReconnect();
        }
    };


    /**
     * 重连Handler
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_RECONNECT:
                    mHandler.removeMessages(MSG_RECONNECT);
                    if (iUIConnect == null) {
                        bindRemoteService();
                    }
                    break;
            }
        }
    };


    public void registerCallback(IServerCallback callback) {
        if (iUIConnect != null && addCallback(callback)) {
            try {
                iUIConnect.registerCallback(getCallback(callback.toString()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void unregisterCallback(IServerCallback callback) {
        if (iUIConnect != null && checkCallback(callback)) {
            try {
                iUIConnect.unregisterCallback(getCallback(callback.toString()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void changeServer(ChangeServerBean changeServerBean) {
        if (iUIConnect != null) {
            try {
                iUIConnect.changeServer(changeServerBean);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public ServerDetailBean getServerContent() {
        if (iUIConnect != null) {
            try {
                return iUIConnect.getServerContent();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    private boolean addCallback(final IServerCallback callback) {
        if (callback == null) {
            return false;
        }
        synchronized (callbackMap) {
            String key = callback.toString();
            if (!callbackMap.containsKey(key)) {
                ServerCallbackImpl impl = new ServerCallbackImpl(callback);
                callbackMap.put(key, impl);
                return true;
            }
            return false;
        }
    }


    private boolean checkCallback(final IServerCallback callBack) {
        if (callBack == null) {
            return false;
        }
        synchronized (callbackMap) {
            String key = callBack.toString();
            return callbackMap.containsKey(key);
        }
    }

    private IServerConnect getCallback(String key) {
        synchronized (callbackMap) {
            return callbackMap.get(key);
        }
    }
}
