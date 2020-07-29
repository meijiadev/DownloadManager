package com.ddr.ezreal;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.model.GSYVideoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:播放视频管理类
 * time:2020/07/28
 */
public class VideoPlayerManager {
    public static VideoPlayerManager videoPlayerManager;
    private List<GSYVideoModel> videoModels;

    public static VideoPlayerManager getInstance() {
        if (videoPlayerManager==null){
            videoPlayerManager=new VideoPlayerManager();
        }
        return videoPlayerManager;
    }

    private VideoPlayerManager() {
        super();
    }

    public void setVideoModels(List<GSYVideoModel> videoModels) {
        this.videoModels = videoModels;

    }

    public List<GSYVideoModel> getVideoModels() {
        return (videoModels==null)?new ArrayList<>():videoModels;
    }


}
