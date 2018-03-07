package com.shouxiu.wanandroid.weex.module;

import android.text.TextUtils;

import com.shouxiu.wanandroid.weex.utils.AppletConstant;
import com.shouxiu.wanandroid.weex.utils.Utils;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *  @version 1.0
 *  @description         文件操作模块
 */

public class UmsFileModule extends UmsBasicModule {
    private enum Operation {SAVE_FILE, GET_SVAE_LIST, GET_FILE_INFO, REMOVE_FILE}

    private Operation operation;

    private JSCallback saveFileCallback;
    private JSCallback getListCallback;
    private JSCallback getInfoCallback;
    private JSCallback removeCallback;

    private String mFilePath;

    /**
     * 将文件保存到指定位置
     *
     * @param filePath 临时文件路径
     * @param callback JS回调
     */
    @JSMethod
    public void saveFile(String filePath, JSCallback callback) {
        operation = Operation.SAVE_FILE;
        saveFileCallback = callback;
        mFilePath = filePath;
        requestStoragePermission();
    }

    /**
     * 获取保存的文件列表
     *
     * @param callback JS回调
     */
    @JSMethod
    public void getSavedFileList(JSCallback callback) {
        operation = Operation.GET_SVAE_LIST;
        getListCallback = callback;
        requestStoragePermission();
    }

    /**
     * 获取文件相关信息
     *
     * @param filePath 文件路径
     * @param callback JS回调
     */
    @JSMethod
    public void getSavedFileInfo(String filePath, JSCallback callback) {
        operation = Operation.GET_FILE_INFO;
        getInfoCallback = callback;
        mFilePath = filePath;
        requestStoragePermission();
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     * @param callback JS回调
     */
    @JSMethod
    public void removeSavedFile(String filePath, JSCallback callback) {
        operation = Operation.REMOVE_FILE;
        removeCallback = callback;
        mFilePath = filePath;
        requestStoragePermission();
    }

    @Override
    protected void onRequestStorageResult(boolean hasPermission) {
        super.onRequestStorageResult(hasPermission);
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            switch (operation) {
                case SAVE_FILE:
                    if (TextUtils.isEmpty(mFilePath)) {
                        callBySimple(false, saveFileCallback);
                        return;
                    }
                    File old = Utils.FileUtil.newFile(mFilePath);
                    String name = old.getName();
                    File dir = Utils.FileUtil.newFile(AppletConstant.Path.SAVED);

                    boolean isSaved = false;
                    //验证文件是否存在
                    if (old.exists() && dir.exists()) {
                        String path = AppletConstant.Path.SAVED + name;
                        isSaved = Utils.FileUtil.copyTo(mFilePath, path);

                result.put("path", path);
                        callByResult(isSaved, result, saveFileCallback);
            } else {
                        callBySimple(isSaved, saveFileCallback);
            }
                    break;
                case GET_SVAE_LIST:
                    File saved = Utils.FileUtil.newFile(AppletConstant.Path.SAVED);
                    ArrayList<String> array = new ArrayList<String>();
                    if (saved.exists()) {
                        File[] files = saved.listFiles();
                        for (File file : files) {
                            if (file != null && file.exists()) {
                                array.add(file.getAbsolutePath());
                            }
                        }
                    }
                    result.put("fileList", array);
                    callByResult(true, result, getListCallback);
                    break;
                case GET_FILE_INFO:
                    if (!Utils.FileUtil.isFile(mFilePath)) {
                        callBySimple(false, getInfoCallback);
                        return;
                    }
                    File file = Utils.FileUtil.newFile(mFilePath);
                    if (file.exists()) {
                        result.put("size", file.length() + "");
                        result.put("createTime", file.lastModified() + "");
                        callByResult(true, result, getInfoCallback);
                    } else {
                        callBySimple(false, getInfoCallback);
                    }
                    break;
                case REMOVE_FILE:
                    if (!Utils.FileUtil.isFile(mFilePath)) {
                        callBySimple(false, removeCallback);
                return;
            }
                    callBySimple(Utils.FileUtil.delete(mFilePath), removeCallback);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            switch (operation) {
                case SAVE_FILE:
                    callBySimple(false, saveFileCallback);
                    break;
                case GET_SVAE_LIST:
                    callBySimple(false, getListCallback);
                    break;
                case GET_FILE_INFO:
                    callBySimple(false, getInfoCallback);
                    break;
                case REMOVE_FILE:
                    callBySimple(false, removeCallback);
                    break;
            }
        }
    }
}
