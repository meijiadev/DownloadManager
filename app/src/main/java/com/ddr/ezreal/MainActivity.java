package com.ddr.ezreal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ddr.ezreal.download.DownloadService;
import com.ddr.ezreal.download.DownloadTak;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btStartService,btStopService,btBindService,btUnbindService;
    private boolean isBindService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btStartService=findViewById(R.id.btStartService);
        btStopService=findViewById(R.id.btStopService);
        btBindService=findViewById(R.id.btBindService);
        btUnbindService=findViewById(R.id.btUnbindService);

        btStartService.setOnClickListener(this);
        btStopService.setOnClickListener(this);
        btBindService.setOnClickListener(this);
        btUnbindService.setOnClickListener(this);
    }

    private DownloadService.DownloadBinder downloadBinder;
    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.e("------ServiceConnection","onServiceConnected");
            downloadBinder= (DownloadService.DownloadBinder) iBinder;
            isBindService=true;
            downloadBinder.startDownload();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e("------ServiceConnection","onServiceDisconnected");
            isBindService=false;
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btStartService:
                /*Intent startIntent=new Intent(this,DownloadService.class);
                startService(startIntent);*/
                new DownloadTak().execute();
                break;
            case R.id.btStopService:
                Intent stopIntent=new Intent(this,DownloadService.class);
                stopService(stopIntent);
                break;
            case R.id.btBindService:
                Intent bindIntent=new Intent(this,DownloadService.class);
                bindService(bindIntent,serviceConnection,BIND_AUTO_CREATE);
                break;
            case R.id.btUnbindService:
                if (isBindService)
                unbindService(serviceConnection);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBindService)
        unbindService(serviceConnection);
    }
}
