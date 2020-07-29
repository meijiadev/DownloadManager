package com.ddr.ezreal.download;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class DownloadService extends Service {
    private DownloadBinder downloadBinder=new DownloadBinder();
    public DownloadService() {
    }
    public boolean isDownloading;
    public int progress;

    public class DownloadBinder extends Binder{
        public DownloadBinder() {
            super();
            Log.e("DownloadBinder:","------初始化DownloadBinder");
        }

        public void startDownload(){
            isDownloading=true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (isDownloading){
                        progress++;
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (progress==100){
                            isDownloading=false;
                            stopSelf();
                        }
                        Log.e("当前下载进度：","------"+progress);
                    }
                }
            }).start();
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.e("DownloadBinder:","------onBind");
      return downloadBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("DownloadBinder:","------onUnbind");
        return super.onUnbind(intent);
    }
    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.e("DownloadBinder:","------onRebind");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("DownloadService","------onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("DownloadService","------onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("DownloadService","------onDestroy");
    }




}
