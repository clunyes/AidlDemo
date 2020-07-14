package com.zk.mylibrary.server2ui;

import com.zk.mylibrary.bean.ChangeUiBean;

/**
 * @author kang.zhao
 * @ClassName: IServerCallback
 * @date 2020-07-14 16:01
 * @Description:
 */
public interface IServerCallback {

    void serverChangeUI(ChangeUiBean changeUiBean);
}
