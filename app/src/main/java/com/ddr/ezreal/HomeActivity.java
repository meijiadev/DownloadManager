package com.ddr.ezreal;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.ddr.ezreal.adapter.VideoListAdapter;
import com.ddr.ezreal.bean.VideoMessage;
import com.ddr.ezreal.bean.VideoModel;
import com.ddr.ezreal.http.HttpManager;
import com.shuyu.gsyvideoplayer.model.GSYVideoModel;
import com.shuyu.gsyvideoplayer.video.GSYSampleADVideoPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

import static com.ddr.ezreal.http.Api.APP_DEFAULT_DOMAIN;
import static com.ddr.ezreal.http.Api.VIDEO_LIST_DOMAIN_NAME;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.videoRecycler)
    RecyclerView videoRecycler;

    private VideoListAdapter videoListAdapter;
    private List<VideoMessage.TrailersBean> videos=new ArrayList<>();

    private List<VideoModel> videoModels=new ArrayList<>();
    @Override
    protected int getLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        super.initView();
        RetrofitUrlManager.getInstance().putDomain(VIDEO_LIST_DOMAIN_NAME,APP_DEFAULT_DOMAIN);
        videoListAdapter=new VideoListAdapter(R.layout.item_video_list);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        videoRecycler.setLayoutManager(linearLayoutManager);
        videoRecycler.setAdapter(videoListAdapter);
        onItemClick();
    }

    @Override
    protected void initData() {
        super.initData();
        Log.e("-----initData",RetrofitUrlManager.getInstance().fetchDomain(VIDEO_LIST_DOMAIN_NAME).toString());
        HttpManager.getInstance().getHttpServer().requestVideoList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<VideoMessage>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e("-----onSubscribe","");
            }

            @Override
            public void onNext(VideoMessage videoList) {
                Log.e("-----onNext","");
                videos=videoList.getTrailers();
                videoListAdapter.setNewData(videos);
                videoModels.clear();
                for (VideoMessage.TrailersBean trailersBean:videos){
                    VideoModel videoModel=new VideoModel(trailersBean.getHightUrl(),trailersBean.getMovieName());
                    videoModels.add(videoModel);
                }
                VideoPlayerManager.getInstance().setVideoModels(Collections.unmodifiableList(videoModels));
            }

            @Override
            public void onError(Throwable e) {
                Log.e("onError:",e.getMessage());
                Toast.makeText(HomeActivity.this,"电影列表加载失败!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                Log.e("-----onComplete","");
            }
        });
    }


    private void onItemClick(){
        videoListAdapter.setOnItemClickListener((adapter, view, position) -> {
            Log.e("onItemClick","----------------d");
            Intent intent=new Intent(HomeActivity.this,VideoActivity.class);
            intent.putParcelableArrayListExtra("videoUrl", (ArrayList<? extends Parcelable>) videoModels);
            intent.putExtra("videoPosition",position);
            startActivity(intent);
        });
    }

}
