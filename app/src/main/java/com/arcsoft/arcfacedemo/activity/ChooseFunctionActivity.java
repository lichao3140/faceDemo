package com.arcsoft.arcfacedemo.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.arcsoft.arcfacedemo.R;
import com.arcsoft.arcfacedemo.common.Constants;
import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.FaceEngine;


public class ChooseFunctionActivity extends Activity {

    private static final int ACTION_REQUEST_PERMISSIONS = 0x001;
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_function);

    }

    /**
     * 打开相机，显示年龄性别
     *
     * @param view
     */
    public void jumpToPreviewActivity(View view) {
        startActivity(new Intent(this, PreviewActivity.class));
    }

    /**
     * 处理单张图片，显示图片中所有人脸的信息，并且一一比对相似度
     *
     * @param view
     */
    public void jumpToSingleImageActivity(View view) {
        startActivity(new Intent(this, SingleImageActivity.class));
    }

    /**
     * 选择一张主照，显示主照中人脸的详细信息，然后选择图片和主照进行比对
     *
     * @param view
     */
    public void jumpToMultiImageActivity(View view) {
        startActivity(new Intent(this, MultiImageActivity.class));
    }
    /**
     * 打开相机，人脸注册，人脸识别
     *
     * @param view
     */
    public void jumpToFaceRecognizeActivity(View view) {
        startActivity(new Intent(this, RegisterAndRecognizeActivity.class));
    }

    public void jumpToBatchRegisterActivity(View view) {
        startActivity(new Intent(this, FaceManageActivity.class));
    }
    /**
     * 激活引擎
     *
     * @param view
     */
    public void activeEngine(final View view) {
        if (!checkPermissions(NEEDED_PERMISSIONS)){
            ActivityCompat.requestPermissions(this, NEEDED_PERMISSIONS, ACTION_REQUEST_PERMISSIONS);
            return;
        }
        if (view != null) {
            view.setClickable(false);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                FaceEngine faceEngine = new FaceEngine();
                final int activeCode = faceEngine.active(ChooseFunctionActivity.this, Constants.ACTIVE_KEY,Constants.APP_ID, Constants.SDK_KEY);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (activeCode == ErrorInfo.MOK) {
                            showToast(getString(R.string.active_success));
                        } else if (activeCode == ErrorInfo.MERR_ASF_BASE_ALREADY_ACTIVATED) {
                            showToast(getString(R.string.already_activated));
                        } else {
                            showToast(getString(R.string.active_failed, activeCode));
                        }

                        if (view != null) {
                            view.setClickable(true);
                        }
                    }
                });

            }
        }).start();
    }

    private boolean checkPermissions(String[] neededPermissions) {
        if (neededPermissions == null || neededPermissions.length == 0) {
            return true;
        }
        boolean allGranted = true;
        for (String neededPermission : neededPermissions) {
            allGranted &= ContextCompat.checkSelfPermission(this, neededPermission) == PackageManager.PERMISSION_GRANTED;
        }
        return allGranted;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ACTION_REQUEST_PERMISSIONS) {
            boolean isAllGranted = true;
            for (int grantResult : grantResults) {
                isAllGranted &= (grantResult == PackageManager.PERMISSION_GRANTED);
            }
            if (isAllGranted) {
                activeEngine(null);
            } else {
                showToast(getString(R.string.permission_denied));
            }
        }
    }

    Toast toast = null;

    private void showToast(String s) {
        if (toast == null) {
            toast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            toast.setText(s);
            toast.show();
        }
    }

}
