package com.example.zhangdede.servicetest;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by zhangdede on 2016/9/25.
 */
public class SelfService extends Service {
    private SelfBinder selfBinder = null;
    private Boolean runFlag = false;
    @Nullable
    @Override

    public IBinder onBind(Intent intent) {
        Log.i("SelfService","onBind");
        return selfBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Intent notificationIntent = new Intent(this,SelfService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);
        Notification notification = new Notification.Builder(this).setContentText("first test")
                .setContentTitle("test").setOngoing(true).setSmallIcon(R.drawable.ic_launcher).setContentIntent(pendingIntent).build();

        startForeground(101,notification);
        selfBinder = new SelfBinder();

        Log.i("SelfService","onCreate");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("SelfService","onStartCommand");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("SelfService","onDestroy-> execing");
        this.stopSelf();
        runFlag = false;
    }

    class SelfBinder extends Binder {

        private int count = 0;
        public SelfBinder() {
            super();
        }
     public void DownloadTask()
     {
         runFlag = true;
         new Thread(new Runnable() {
             @Override
             public void run() {
                 while(runFlag)
                 {
                     for(int i = 0;i < 20000;i++)
                         for(int j = 0;j < 20000;j++);
                     count++;
                     Log.i("SelfService",String.valueOf(count));
                     Log.i("SelfService",String.valueOf(runFlag));
                 }
             }
         }).start();

     }
        public int getProgess(){
            return count;
        }
    }
}
