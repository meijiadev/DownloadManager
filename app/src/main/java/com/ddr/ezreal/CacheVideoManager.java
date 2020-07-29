package com.ddr.ezreal;

import com.shuyu.gsyvideoplayer.GSYVideoManager;

/**
 * 主动缓存视频的类
 */
public class CacheVideoManager  {
    //GSYVideoManager gsyVideoManager;
    private static CacheVideoManager cacheVideoManager;



    public static CacheVideoManager getInstance(){
        if (cacheVideoManager==null){
            cacheVideoManager=new CacheVideoManager();
        }
        return cacheVideoManager;
    }

    private CacheVideoManager(){
        //gsyVideoManager=GSYVideoManager.instance();
    }

    public void videoPrepare(){

    }
}
