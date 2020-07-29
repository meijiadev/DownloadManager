package com.ddr.ezreal.download;

import android.os.AsyncTask;
import android.util.Log;

public class DownloadTak extends AsyncTask<Void,Integer,Boolean> {
    private int downloadProgress;
    /**
     * 后台任务执行前调用，如界面的初始化操作，显示进度框...
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * 子线程运行，处理耗时任务，任务完后通过return语句返回执行结果
     * @param voids
     * @return
     */
    @Override
    protected Boolean doInBackground(Void... voids) {
        while (downloadProgress<100){
            downloadProgress++;
            publishProgress(downloadProgress);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Log.e("当前下载进度：","------"+downloadProgress);
        }
        return true;
    }

    /**
     * 在后台任务中（doInBackground）调用 publishProgress()后执行该方法
     * @param values
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Log.e("DownloadTak","下载进度："+values[0]);
    }


    /**
     * 当后台任务执行完毕并通过return语句进行返回时
     * @param aBoolean
     */
    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        Log.e("-----DownloadTak","onPostExecute"+aBoolean);
    }

    @Override
    protected void onCancelled(Boolean aBoolean) {
        super.onCancelled(aBoolean);
    }
}
