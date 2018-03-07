package com.shouxiu.wanandroid.weex.module;

import android.Manifest;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.shouxiu.wanandroid.weex.utils.AppletConstant;
import com.shouxiu.wanandroid.weex.utils.Utils;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @description 设备信息和信息电话通讯模块
 */
public class UmsDeviceModule extends UmsBasicModule {
    private enum QueryTag {SELECTED, MATCHING}

    private QueryTag tag;
    // 所需的权限
    private String[] permissions_contact = new String[]{Manifest.permission.READ_CONTACTS};
    private String[] permissions_call = new String[]{Manifest.permission.CALL_PHONE};
    private String[] permissions_sms = new String[]{Manifest.permission.SEND_SMS};
    private JSCallback contactCallback;
    private JSCallback locatContactCallback;
    private JSCallback callPhoneCallback;
    private JSCallback smsCallback;
    private String phoneNo;
    private String smsBody;

    /**
     * 获取设备信息
     *
     * @param callback 执行结果JS回调
     */
    @JSMethod
    public void getDeviceInfo(JSCallback callback) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            //获取屏幕参数
            WindowManager manager = getActivity().getWindow().getWindowManager();
            DisplayMetrics metric = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(metric);
            //获取可绘制区域的高宽
            Rect rect = new Rect();
            getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("model", Build.DEVICE);
            data.put("pixelRatio", metric.density + "");// 屏幕密度
            data.put("screenWidth", metric.widthPixels + "");// 屏幕宽度（像素）
            data.put("screenHeight", metric.heightPixels + "");// 屏幕高度（像素）
            data.put("windowWidth", mWXSDKInstance.getWeexWidth() + "");
            data.put("windowHeight", mWXSDKInstance.getWeexHeight() + "");
            data.put("system", Build.VERSION.RELEASE);
            data.put("platform", "Android");
            data.put("SDKVersion", Build.VERSION.SDK_INT + "");
            result.put("data", data);
            callByResult(true, result, callback);
        } catch (Exception e) {
            e.printStackTrace();
            callBySimple(false, callback);
        }
    }

    /**
     * 拨打电话
     *
     * @param phoneNo  目标电话号码
     * @param callback 执行结果JS回调
     */
    @JSMethod
    public void callTo(String phoneNo, JSCallback callback) {
        try {
            this.phoneNo = phoneNo;
            this.callPhoneCallback = callback;
            //检查是否是合法手机号
            if (TextUtils.isEmpty(phoneNo) || !phoneNo.matches(AppletConstant.Regex.PHONE_NUM)) {
                callBySimple(false, callback);
                return;
            }

            if (hasPermission(permissions_call)) {
                openCallPhoneActivity();
            } else {
                ActivityCompat.requestPermissions(getActivity(), permissions_call, AppletConstant.Code.REQUEST_CALL_PERMISSION_RESULT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            callBySimple(false, callback);
        }
    }

    /**
     * 发送短信
     *
     * @param phoneNo  目标电话号码
     * @param content  短信内容
     * @param callback 执行结果JS回调
     */
    @JSMethod
    public void smsTo(String phoneNo, String content, JSCallback callback) {
        try {
            this.phoneNo = phoneNo;
            smsCallback = callback;
            smsBody = content;
            //检查是否是合法手机号
            if (TextUtils.isEmpty(phoneNo) || !phoneNo.matches(AppletConstant.Regex.PHONE_NUM)) {
                callBySimple(false, callback);
                return;
            }
            if (hasPermission(permissions_sms)) {
                openSendSMSActivity();
            } else {
                ActivityCompat.requestPermissions(getActivity(), permissions_sms, AppletConstant.Code.REQUEST_SMS_PERMISSION_RESULT);
            }

        } catch (Exception e) {
            e.printStackTrace();
            callBySimple(false, callback);
        }
    }

    /**
     * 获取手机联系人
     *
     * @param callback 执行结果JS回调
     */
    @JSMethod
    public void getContact(JSCallback callback) {
        try {
            tag = QueryTag.SELECTED;
            contactCallback = callback;
            if (hasPermission(permissions_contact)) {
                openSelectedContentActivity();
            } else {
                ActivityCompat.requestPermissions(getActivity(), permissions_contact, AppletConstant.Code.REQUEST_CONTACT_PERMISSION_RESULT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            callBySimple(false, callback);
        }
    }

    /**
     * 匹配手机联系人
     *
     * @param filter 查询基础数据
     * @param matchType  查询类型  fuzzy:模糊匹配   exact:精确匹配     experssion:正则表达式匹配
     * @param fields 查询字段
     * @param maxNum 最大返回条目
     * @param callback 执行结果JS回调
     */
    private String filter;
    private String matchType = "fuzzy";
    private List<String> fields = new ArrayList<String>();
    private int maxNum;

    @JSMethod
    public void getLocalContact(String params, JSCallback callback) {
        try {
            JSONObject objcet = new JSONObject(params);
            this.filter = objcet.optString("filter");
            this.matchType = objcet.has("matchType") ? objcet.optString("matchType") : "fuzzy";
            this.maxNum = objcet.has("maxNum") ? objcet.optInt("maxNum") : -1;
            JSONArray array = objcet.optJSONArray("fields");
            if (array != null && array.length() > 0) {
                for (int i = 0; i < array.length(); i++) {
                    fields.add(array.optString(i));
                }
            }
            tag = QueryTag.MATCHING;
            locatContactCallback = callback;
            if (hasPermission(permissions_contact)) {
                loadContact(null);
            } else {
                ActivityCompat.requestPermissions(getActivity(), permissions_contact, AppletConstant.Code.REQUEST_CONTACT_PERMISSION_RESULT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            callBySimple(false, callback);
        }
    }

    /**
     * 启动系统选则联系人界面
     */
    private void openSelectedContentActivity() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setData(ContactsContract.Contacts.CONTENT_URI);
        getActivity().startActivityForResult(intent, AppletConstant.Code.SELECTE_LINKMAN_RESULT);
    }


    /**
     * 启动拨打电话界面
     */
    private void openCallPhoneActivity() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNo));
        getActivity().startActivity(intent);
        callBySimple(true, callPhoneCallback);
    }

    /**
     * 启动发送短信界面
     */
    private void openSendSMSActivity() {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNo));
        intent.putExtra("sms_body", smsBody);
        getActivity().startActivity(intent);
        callBySimple(true, smsCallback);
    }

    /**
     * 从数据库获取联系人
     */
    private void loadContact(final Intent data) {
        LoaderManager.LoaderCallbacks<Cursor> callback = new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                CursorLoader loader = new CursorLoader(getActivity());
                loader.setUri(tag == QueryTag.SELECTED ? data.getData() : ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                HashMap<String, Object> result = new HashMap<String, Object>();
                List<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
                ContentResolver resolverol = getActivity().getContentResolver();
                cursor.moveToFirst();
                Cursor phones;
                // 条件为联系人ID
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                if (tag == QueryTag.SELECTED) {
                    phones = resolverol.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                } else {
                    phones = resolverol.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
                }
                while (phones.moveToNext()) {
                    String phoneNo = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    String lable = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    phoneNo = phoneNo == null ? "" : phoneNo.replace("-", "").replace("+86", "").replace(" ", "").trim();
                    String putStr;
                    if (tag == QueryTag.SELECTED) {
                        result.put("phoneNumber", phoneNo);
                        result.put("name", lable);
                    } else {
                        HashMap<String, String> item = new HashMap<String, String>();
                        putStr = fields.contains("phoneNumbers") ? item.put("phoneNumber", phoneNo) : null;
                        putStr = fields.contains("userName") ? item.put("userName", lable) : null;
                        if (fields.contains("email")) {
                            contactId = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Email.CONTACT_ID));
                            Cursor emails = resolverol.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId, null, null);
                            while (emails.moveToNext()) {
                                item.put("email", emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)));
                            }
                            Utils.CloseableUtil.close(emails);
                        }
                        if (TextUtils.equals("fuzzy", matchType)) {
                            if (phoneNo.contains(filter)) {
                                contactList.add(item);
                            }
                        }
                        if (TextUtils.equals("exact", matchType)) {
                            if (TextUtils.equals(filter, phoneNo)) {
                                contactList.add(item);
                            }
                        }
                        if (TextUtils.equals("experssion", matchType)) {
                            if (phoneNo.matches(filter)) {
                                contactList.add(item);
                            }
                        }
                    }
                }
                if (tag == QueryTag.SELECTED) {
                    callByResult(true, result, contactCallback);
                } else {
                    result.put("contactList", maxNum == -1 ? contactList: contactList.subList(0, maxNum));
                    callByResult(true, result, locatContactCallback);
                }
                Utils.CloseableUtil.close(cursor);
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {

            }
        };
        getActivity().getLoaderManager().restartLoader(1, null, callback);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case AppletConstant.Code.REQUEST_CONTACT_PERMISSION_RESULT:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (tag == QueryTag.SELECTED) {
                        openSelectedContentActivity();
                    } else {
                        loadContact(null);
                    }
                } else {
                    showToast("通讯录授权失败");
                    if (tag == QueryTag.SELECTED) {
                        callBySimple(false, contactCallback);
                    } else {
                        callBySimple(false, locatContactCallback);
                    }
                }
                break;
            case AppletConstant.Code.REQUEST_CALL_PERMISSION_RESULT:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCallPhoneActivity();
                } else {
                    showToast("拨打电话授权失败");
                    callBySimple(false, callPhoneCallback);
                }
            case AppletConstant.Code.REQUEST_SMS_PERMISSION_RESULT:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openSendSMSActivity();
                } else {
                    showToast("发送短信授权失败");
                    callBySimple(false, smsCallback);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppletConstant.Code.SELECTE_LINKMAN_RESULT) {
            if (resultCode == Activity.RESULT_OK) {
                loadContact(data);
            } else {
                callBySimple(false, contactCallback);
            }
        }
    }
}
