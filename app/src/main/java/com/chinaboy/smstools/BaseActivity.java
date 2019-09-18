package com.chinaboy.smstools;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.chinaboy.smstools.tools.PermissionUtil;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 检查权限
     *
     * @param permission
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean checkPermission(String permission) {
        boolean b = PermissionUtil.checkPermissionWrapper(this, permission);
        return b;
    }

    /**
     * 申请权限
     *
     * @param permission
     * @param requestCode
     */
    public void requestPerssion(String[] permission, int requestCode) {
        PermissionUtil.requestPermissionsWrapper(this, permission, requestCode);
    }
}
