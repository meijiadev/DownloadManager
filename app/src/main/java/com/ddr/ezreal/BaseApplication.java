package com.ddr.ezreal;

import android.app.Application;
import android.content.Context;

import com.shuyu.gsyvideoplayer.cache.CacheFactory;
import com.shuyu.gsyvideoplayer.cache.ProxyCacheManager;
import com.shuyu.gsyvideoplayer.player.PlayerFactory;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;

import tv.danmaku.ijk.media.exo2.Exo2PlayerManager;
import tv.danmaku.ijk.media.exo2.ExoPlayerCacheManager;

import static com.shuyu.gsyvideoplayer.utils.GSYVideoType.SCREEN_TYPE_DEFAULT;


public class BaseApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        //EXOPlayer内核，支持格式更多
        PlayerFactory.setPlayManager(Exo2PlayerManager.class);
        //exo缓存模式，支持m3u8，只支持exo
        CacheFactory.setCacheManager(ProxyCacheManager.class);
        GSYVideoType.setShowType(SCREEN_TYPE_DEFAULT);
    }

    public static Context getContext() {
        return context;
    }
}
