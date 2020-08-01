package com.ddr.ezreal;

import android.content.Context;

import com.shuyu.gsyvideoplayer.cache.CacheFactory;
import com.shuyu.gsyvideoplayer.cache.ICacheManager;
import com.shuyu.gsyvideoplayer.model.GSYVideoModel;
import com.shuyu.gsyvideoplayer.player.IPlayerManager;
import com.shuyu.gsyvideoplayer.player.PlayerFactory;

import java.io.File;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import tv.danmaku.ijk.media.exo2.Exo2PlayerManager;
import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * 主动缓存视频的类
 */
public class CacheVideoManager implements ICacheManager.ICacheAvailableListener {
    //GSYVideoManager gsyVideoManager;
    private static CacheVideoManager cacheVideoManager;
    private Context context;
    private List<GSYVideoModel> modelList;
    private int currentPosition;
    private File mCachePath;

    /**
     播放内核管理
     */
    protected IPlayerManager playerManager;

    /**
     缓存管理
     */
    protected ICacheManager cacheManager;


    protected IPlayerManager getPlayManager() {
        return PlayerFactory.getPlayManager();
    }

    protected ICacheManager getCacheManager() {
        return CacheFactory.getCacheManager();
    }


    public static CacheVideoManager getInstance(Context context){
        if (cacheVideoManager==null){
            cacheVideoManager=new CacheVideoManager(context);
        }
        return cacheVideoManager;
    }

    private CacheVideoManager(Context context){
        this.context=context;
    }

    /**
     * 指定缓存
     * @param
     * @param mapHeadData
     * @param mCachePath
     */
    public void setVideoCache(List<GSYVideoModel>models, int position, Map<String, String> mapHeadData, File mCachePath){
        playerManager = getPlayManager();
        cacheManager = getCacheManager();
        this.modelList=models;
        this.currentPosition=position;
        this.mCachePath=mCachePath;
        Logger.e("------");
        if (cacheManager instanceof NProxyCacheManager &&playerManager instanceof Exo2PlayerManager){
            cacheManager.setCacheAvailableListener(this);
            downloadVideo();
        }
    }

    private void downloadVideo(){
        Logger.e("开始下载"+currentPosition+";"+modelList.size()+modelList.get(currentPosition).getUrl());
       if (currentPosition<modelList.size()-1){
           if (cacheManager.cachePreview(context,mCachePath,modelList.get(currentPosition).getUrl())){
               currentPosition++;
               release();
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               downloadVideo();
               return;
           }
           IMediaPlayer mediaPlayer = playerManager.getMediaPlayer();
           mediaPlayer.prepareAsync();
       }
    }




    /**
     清除缓存

     @param cacheDir 缓存目录，为空是使用默认目录
     @param url      指定url缓存，为空时清除所有
     */
    public void clearDefaultCache(Context context, @Nullable File cacheDir, @Nullable String url) {
        if (cacheManager != null) {
            cacheManager.clearCache(context, cacheDir, url);
        } else {
            if (getCacheManager() != null) {
                getCacheManager().clearCache(context, cacheDir, url);
            }
        }
    }

    public void release(){
        if (playerManager != null) {
            playerManager.release();
            cacheManager.release();
        }
    }
    private int message;
    @Override
    public void onCacheAvailable(File cacheFile, String url, int percentsAvailable) {
        Logger.e("--------缓存进度："+percentsAvailable);
        if (percentsAvailable==100){
            message++;
            if (message>=2){
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                release();
                if (modelList.size()-1>currentPosition){
                    currentPosition++;
                    downloadVideo();
                }
            }
        }else if (percentsAvailable==0){
            message=0;
        }
    }



    public void unRegister(){
        if (cacheManager != null) {
            cacheManager.setCacheAvailableListener(null);
        }
    }


}
