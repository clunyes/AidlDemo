// IUIConnect.aidl
package com.zk.mylibrary;

// Declare any non-default types here with import statements
import com.zk.mylibrary.IServerConnect;
import com.zk.mylibrary.bean.ChangeServerBean;
import com.zk.mylibrary.bean.ServerDetailBean;
interface IUIConnect {

        void registerCallback(IServerConnect callback);

        void unregisterCallback(IServerConnect callback);

        void changeServer(inout ChangeServerBean changeServerBean);

        ServerDetailBean getServerContent();
}
