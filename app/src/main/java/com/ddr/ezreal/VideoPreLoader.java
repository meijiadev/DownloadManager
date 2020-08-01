package com.ddr.ezreal;

import com.danikula.videocache.HttpProxyCacheServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * 视频预加载
 */
public class VideoPreLoader {

    static VideoPreLoader videoPreLoader;


    public static VideoPreLoader getInstance() {
        if (videoPreLoader==null){
            videoPreLoader=new VideoPreLoader();
        }
        return videoPreLoader;
    }

    private VideoPreLoader(){

    }

    /**
     * 制定下载队列中的视频
     * @param urls
     */
    public void setPreLoadUrls(List<String> urls){
        new Thread(()->{
            for (int i=0;i<urls.size();i++){
                realPreload(urls.get(i));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void addPreloadUrl(String url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                realPreload(url);
            }
        }).start();
    }

    /**
     * 正式开始下载
     * @param url
     */
    private  void realPreload(String url){
        HttpURLConnection connection;
        NProxyCacheManager nProxyCacheManager=NProxyCacheManager.instance();
        HttpProxyCacheServer httpProxyCacheServer=nProxyCacheManager.getProxy(BaseApplication.getContext(),GlobalParameter.getDownloadFile());
        String mUrl=httpProxyCacheServer.getProxyUrl(url);
        Logger.e("---------:"+mUrl);
        if (mUrl.contains("http")){
            try {
                URL myURL=new URL(mUrl);
                connection= (HttpURLConnection) myURL.openConnection();
                connection.connect();
                InputStream inputStream=connection.getInputStream();
                byte[]buffer=new byte[1024];
                int download=0;
                do {
                    int numRed=inputStream.read(buffer);
                    download+=numRed;
                    Logger.e("读取下载进度："+download/1024/1024);
                    if (numRed==-1){
                        break;
                    }
                }while (true);
                inputStream.close();
                Logger.e("读取下载完毕！");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
