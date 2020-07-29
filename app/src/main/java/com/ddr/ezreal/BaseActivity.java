package com.ddr.ezreal;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaseActivity extends AppCompatActivity {
    /**
     * ButterKnife 注解
     */
    private Unbinder mButterKnife;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayout()>0){
            setContentView(getLayout());
        }
        mButterKnife= ButterKnife.bind(this);
        initView();
        initData();
    }


    protected int getLayout(){
        return 0;
    }

    protected void initView(){

    }

    protected void initData(){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mButterKnife!=null){
            mButterKnife.unbind();
        }

    }
}
