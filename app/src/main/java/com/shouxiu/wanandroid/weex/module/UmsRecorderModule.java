package com.shouxiu.wanandroid.weex.module;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.support.v4.app.ActivityCompat;

import com.shouxiu.wanandroid.weex.utils.AppletConstant;
import com.shouxiu.wanandroid.weex.utils.Utils;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *  @version 1.0
 *  @description         录音模块
 */

public class UmsRecorderModule extends UmsBasicModule {
    //录音所需权限
    private String[] permissions_record = new String[]{Manifest.permission.RECORD_AUDIO};
    private MediaRecorder recorder;
    private String recordPath;
    private boolean isStarted = true;
    private JSCallback startCallback;
    private JSCallback stopCallback;

    /**
     * 初始化 音频记录器
     * @param callback JS回调
     */
    private void initRecorder(JSCallback callback) {
        try {
            if (recorder == null) {
                recorder = new MediaRecorder();
                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

                File fileDir = Utils.FileUtil.newFile(AppletConstant.Path.SOUND);
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }
            }

            if (recordPath == null) {
                File file = new File(AppletConstant.Path.SOUND, System.currentTimeMillis() + AppletConstant.Suffix.AUDIO);
                recordPath = file.getAbsolutePath();
                recorder.setOutputFile(recordPath);
                recorder.prepare();
            }

        } catch (Exception e) {
            e.printStackTrace();
            callBySimple(false, callback);
        }
    }

    /**
     * 开始录制
     *
     * @param callback JS回调
     */
    @JSMethod
    public void startRecord(JSCallback callback) {
        startCallback = callback;
        isStarted = true;
        requestRecordPermission(callback);
    }
    @JSMethod
    public void stopRecord(JSCallback callback) {
        stopCallback = callback;
        isStarted = false;
        requestRecordPermission(callback);
    }

    private void doStartRecord() {
        try {
            initRecorder(startCallback);
            recorder.start();
            callBySimple(true, startCallback);
        } catch (Exception e) {
            e.printStackTrace();
            callBySimple(false, startCallback);
        }
    }

    private void doStopRecord() {
        try {
            if (recorder != null && recordPath != null) {
                recorder.stop();
                recorder.release();
                recorder = null;

                Map<String, Object> result = new HashMap<String, Object>();
                result.put("path", recordPath);
                callByResult(true, result, stopCallback);
                recordPath = null;
            } else {
                callBySimple(false, stopCallback);
            }
        } catch (Exception e) {
            e.printStackTrace();
            callBySimple(false, stopCallback);
        }
    }

    private void requestRecordPermission(JSCallback callback) {
        if (hasPermission(permissions_record)) {
            initRecorder(callback);
            if (isStarted) {
                doStartRecord();
            } else {
                doStopRecord();
            }
        } else {
            ActivityCompat.requestPermissions(getActivity(), permissions_record, AppletConstant.Code.REQUEST_RECORD_PERMISSION_RESULT);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppletConstant.Code.REQUEST_RECORD_PERMISSION_RESULT && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initRecorder(stopCallback);
            if (isStarted) {
                doStartRecord();
            } else {
                doStopRecord();
            }
        } else {
            showToast("录音设备授权失败");
            if (isStarted) {
                callBySimple(false, startCallback);
            } else {
                callBySimple(false, stopCallback);
            }
        }
    }
}
