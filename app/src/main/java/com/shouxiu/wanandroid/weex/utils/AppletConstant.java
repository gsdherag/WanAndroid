package com.shouxiu.wanandroid.weex.utils;

import android.os.Environment;

/**
 * @version 1.0
 * @description 常量
 */

public interface AppletConstant {

    interface Path {
        String STORAGE = Environment.getExternalStorageDirectory().getPath();

        String ROOT = STORAGE + "/shouxiu/applet/";

        String SOUND = ROOT + "sound/";

        String SAVED = ROOT + "saved/";

        String PHOTO = ROOT + "photo/";

        String VEDIO = ROOT + "video/";
    }

    interface Number {
        float MAX_BRIGHTNESS = 100;

    }

    interface Code {
        int CAPTURE_IMAGE_RESULT = 100;

        int CAPTURE_VIDEO_RESULT = CAPTURE_IMAGE_RESULT + 1;

        int PICK_IMAGE_RESULT = CAPTURE_VIDEO_RESULT + 1;

        int SELECTE_LINKMAN_RESULT = PICK_IMAGE_RESULT + 1;

        int REQUEST_CONTACT_PERMISSION_RESULT = SELECTE_LINKMAN_RESULT + 1;

        int REQUEST_CALL_PERMISSION_RESULT = REQUEST_CONTACT_PERMISSION_RESULT + 1;

        int REQUEST_SMS_PERMISSION_RESULT = REQUEST_CALL_PERMISSION_RESULT + 1;

        int SCAN_QRCODE_RESULT = REQUEST_SMS_PERMISSION_RESULT + 1;

        int REQUEST_STORAGE_PERMISSION_RESULT = SCAN_QRCODE_RESULT + 1;

        int REQUEST_RECORD_PERMISSION_RESULT = REQUEST_STORAGE_PERMISSION_RESULT + 1;
    }

    interface Suffix {
        String VEDIO = ".mov";

        String AUDIO = ".3gp";

        String JPG = ".jpg";
    }

    interface Prefix {
        String IMAGE = "img_";

        String VEDIO = "vid_";
    }

    interface Regex {
        /**
         * 判断是否适合法手机号码的正则表达式
         */
        String PHONE_NUM = "^1\\d{10}$";
    }

    interface Weex {
        String WEEX_TPL_KEY = "_wx_tpl";
        String WXPAGE = "wxpage";
        String WEEX_CATEGORY = "com.taobao.android.intent.category.WEEX";
        String INSTANCEID = "instanceId";
    }
}
