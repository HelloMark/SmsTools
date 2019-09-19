package com.chinaboy.smstools;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

import com.chinaboy.smstools.entrty.MessageBean;
import com.chinaboy.smstools.tools.MessageUtil;

import java.util.List;

public class MainActivity extends BaseActivity {
    private TextView tvContent;

    private int smsRequestCode = 1;
    private int smsReceiveCode = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvContent = findViewById(R.id.sms);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 23){
            boolean isReadMessage = checkPermission(Manifest.permission.READ_SMS);
            if (isReadMessage) {
                readMessage();
            } else {
                requestPerssion(new String[]{Manifest.permission.READ_SMS}, smsRequestCode);
            }

            boolean isReciverMessage = checkPermission(Manifest.permission.RECEIVE_SMS);
            if (isReciverMessage) {
            //    readMessage();
            } else {
                requestPerssion(new String[]{Manifest.permission.RECEIVE_SMS}, smsReceiveCode);
            }
        }else {
            readMessage();
        }
    }

    /**
     * 读取短信
     */
    private void readMessage() {
        List<MessageBean> smsInPhone = MessageUtil.getSmsInPhone(0);
        tvContent.setText(smsInPhone.toString());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (smsRequestCode == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //已经授权
                readMessage();

            } else {
                //点击了不再提示,拒绝权限
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                    //跳转到设置界面
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, 1);

                } else {
                    Toast.makeText(this, "权限拒绝", Toast.LENGTH_SHORT).show();
                }
            }
        }else if (smsReceiveCode == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //已经授权
                //readMessage();

            } else {
                //点击了不再提示,拒绝权限
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                    //跳转到设置界面
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, 2);

                } else {
                    Toast.makeText(this, "权限拒绝", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
}
