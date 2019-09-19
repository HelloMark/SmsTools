package com.chinaboy.smstools;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.util.logging.LogRecord;

public class App extends Application {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        Thread.setDefaultUncaughtExceptionHandler(restartHandler);
    }

    private Thread.UncaughtExceptionHandler restartHandler = new Thread.UncaughtExceptionHandler() {

        public void uncaughtException(Thread thread, Throwable ex) {
    //        restartApp();
        }
    };

    public void restartApp() {
        Intent intent = new Intent(instance,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        instance.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public static App getContext() {
        return instance;
    }

    private Handler mainHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Toast.makeText(getApplicationContext(),(String)msg.obj,Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    public void showToast(String message){
        Message msg = new Message();
        msg.what = 0;
        msg.obj = message;
        mainHandler.sendMessage(msg);
    }
}
