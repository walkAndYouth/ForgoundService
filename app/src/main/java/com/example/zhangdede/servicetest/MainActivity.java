package com.example.zhangdede.servicetest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.ConnectionService;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button Service_Status = null;
    private Button Bind_Service = null;
    private Button GetValue = null;

    private TextView Value_View = null;
    private Boolean start = false;
    private Boolean isBindService = false;

    private SelfService.SelfBinder selfBinder = null;
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            selfBinder = (SelfService.SelfBinder)service;
            Log.i("MainActivity","onServiceConnected");
            selfBinder.DownloadTask();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Service_Status = (Button)findViewById(R.id.service_status);
        Service_Status.setOnClickListener(this);
        Bind_Service = (Button)findViewById(R.id.bindService);
        Bind_Service.setOnClickListener(this);
        GetValue = (Button)findViewById(R.id.getValue);
        GetValue.setOnClickListener(this);
        Value_View = (TextView)findViewById(R.id.textView);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,SelfService.class);
        switch(v.getId()){
            case R.id.service_status:

                if(!start){

                    startService(intent);
                    start = true;
                    Service_Status.setText("Stop Service");
                }
                else{
                    start = false;
                    stopService(intent);
                    Service_Status.setText("Start Service");
                }
                break;
            case R.id.bindService:
                if(!isBindService){
                    bindService(intent,connection,BIND_AUTO_CREATE);
                    isBindService = true;
                    Bind_Service.setText("unbind Service");
                }
                else {
                    unbindService(connection);
                    Bind_Service.setText("bind Service");
                    isBindService = false;
                }
                break;
            case R.id.getValue:
                Value_View.setText(String.valueOf(selfBinder.getProgess()));
                break;
        }
    }
}
