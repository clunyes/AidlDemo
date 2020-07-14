package com.zk.zkvirtual;

import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.zk.mylibrary.bean.ChangeServerBean;
import com.zk.mylibrary.bean.ChangeUiBean;
import com.zk.mylibrary.bean.ServerDetailBean;
import com.zk.mylibrary.server2ui.IServerCallback;
import com.zk.mylibrary.ui2server.ConnectCallBack;
import com.zk.mylibrary.ui2server.IUIConnectApi;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tv_get;
    private TextView tv_change;
    private TextView tv_content;
    private IUIConnectApi api;
    private IServerCallback callback2UI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        tv_change = findViewById(R.id.tv_change);
        tv_get = findViewById(R.id.tv_get);
        tv_content = findViewById(R.id.tv_content);
    }


    private void initData() {
        api = IUIConnectApi.getInstance(this);
        api.init(new ConnectCallBack() {
            @Override
            public void onAPIReady(boolean state) {
                if (state) {
                    reg();
                    ToastUtils.showShort("成功联系lester");
                } else {
                    unReg();
                    ToastUtils.showShort("联系不到lester");
                }
            }
        });
    }

    private void reg() {
        if (callback2UI == null) {
            callback2UI = new IServerCallback() {

                @Override
                public void serverChangeUI(ChangeUiBean changeUiBean) {
                    tv_content.setText(changeUiBean.getContent());
                }
            };
            api.registerCallback(callback2UI);
        }
    }

    private void unReg() {
        api.unregisterCallback(callback2UI);
        callback2UI = null;
    }


    private void initListener() {
        tv_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (api != null) {
                    ServerDetailBean bean = api.getServerContent();
                    if (bean != null) {
                        tv_content.setText(bean.getContent());
                    }
                }
            }
        });
        tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (api != null) {
                    ChangeServerBean changeServerBean = new ChangeServerBean();
                    changeServerBean.setContent("莱斯特，换个事");
                    api.changeServer(changeServerBean);
                }
            }
        });
    }

}
