package com.zk.lester;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.zk.mylibrary.bean.ChangeServerBean;
import com.zk.mylibrary.bean.ChangeUiBean;

import org.greenrobot.eventbus.EventBus;

/**
 * @author kang.zhao
 * @ClassName: LesterService
 * @date 2020-07-14 17:46
 * @Description:
 */
public class LesterService extends Service {
    private ServerImpl mBinder;

    private String curContent;
    private String[] tasks;
    private int index = 0;


    @Override
    public void onCreate() {
        super.onCreate();
        startSelfForeground();
        mBinder = ServerImpl.getInstance(this);
        initService();
    }

    private void initService() {
        tasks = getResources().getStringArray(R.array.task_array);
        curContent = tasks[index];
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private void startSelfForeground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("id", "name", NotificationManager.IMPORTANCE_LOW);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
            Notification notification = new Notification.Builder(this, "id").build();
            startForeground(1, notification);
        }
    }


    @Override
    public void onDestroy() {
        mBinder = null;
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void changeTask(ChangeServerBean changeServerBean) {
        index++;
        curContent = tasks[index % 9];
        ChangeUiBean changeUiBean = new ChangeUiBean();
        changeUiBean.setContent(curContent);
        mBinder.changeUi(changeUiBean);
    }

    public String getServerContent() {
        return curContent;
    }
}
