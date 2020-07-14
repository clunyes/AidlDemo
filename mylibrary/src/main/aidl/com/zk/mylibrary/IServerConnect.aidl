// IServerConnect.aidl
package com.zk.mylibrary;

// Declare any non-default types here with import statements
import com.zk.mylibrary.bean.ChangeUiBean;

interface IServerConnect {

    void serverChangeUI(inout ChangeUiBean changeUiBean);
}
