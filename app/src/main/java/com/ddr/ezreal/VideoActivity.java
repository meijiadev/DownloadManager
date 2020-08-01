package com.ddr.ezreal;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.model.GSYVideoModel;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.GSYSampleADVideoPlayer;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import androidx.appcompat.app.ActionBar;
import butterknife.BindView;

public class VideoActivity extends BaseActivity {
    @BindView(R.id.videoPlayer)
    GSYSampleADVideoPlayer videoPlayer;
    OrientationUtils orientationUtils;
    private List<GSYVideoModel> videoModels=new ArrayList<>();
    @Override
    protected int getLayout() {
        return R.layout.activity_video;
    }

    @Override
    protected void initView() {
        super.initView();
        initState(this);


    }

    @Override
    protected void initData() {
        super.initData();
        Intent intent=getIntent();
        videoModels= Collections.unmodifiableList(intent.getParcelableArrayListExtra("videoUrl"));
        int index=intent.getIntExtra("videoPosition",0);
        videoPlayer.setUp(videoModels,true,index,GlobalParameter.getDownloadFile());
        //设置返回键
        videoPlayer.getBackButton().setVisibility(View.VISIBLE);
        //设置旋转
        orientationUtils = new OrientationUtils(this, videoPlayer);
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orientationUtils.resolveByClick();
            }
        });
        //设置返回按键功能
        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        videoPlayer.startPlayLogic();

    }

    /**
     * 沉浸式状态栏（已适配 ）
     */
    public  void initState(Activity activity) {
        //Logger.e("启动沉浸式状态栏");
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }


    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.onVideoPause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        videoPlayer.onVideoResume();
    }
    @Override
    protected void onStop() {
        super.onStop();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onBackPressed() {
        //先返回正常状态
        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            videoPlayer.getFullscreenButton().performClick();
            return;
        }
        //释放所有
        videoPlayer.setVideoAllCallBack(null);
        super.onBackPressed();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }
}
