package com.shouxiu.wanandroid.utils;

import android.text.Html;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

public class JsonUtils {

    static private Gson gson = new Gson();

    public static JSONObject convert2Json(String jsonStr) {
        return JSON.parseObject(jsonStr);
    }

    public static JSONObject convert2Json(Object object) {
        return convert2Json(gson.toJson(object));
    }

    public static int getInt(JSONObject root, String key) {
        if (!root.containsKey(key)) {
            return 0;
        } else {
            return root.getIntValue(key);
        }
    }

    public static String getString(JSONObject root, String key) {
        if (!root.containsKey(key)) {
            return "";
        } else {
            if (key.equals("memo")) {
                return root.getString(key);
            }
            return Html.fromHtml(root.getString(key)).toString();
        }
    }

    public static double getDouble(JSONObject root, String key) {
        if (!root.containsKey(key)) {
            return 0;
        } else {
            return root.getDouble(key);
        }
    }

    public static JSONArray getJsonArray(JSONObject root, String key) {
        return root.getJSONArray(key);
    }

    public static boolean getBoolean(JSONObject root, String key) {
        String str = getString(root, key);
        return str.equals("true");
    }

    public static JSONObject getJsonObject(String jsonStr) {
        return JSON.parseObject(jsonStr);
    }

    public static <T extends Object> T fromJsonString(String json, Class<T> clz) {
        T resp = gson.fromJson(json, clz);
        return resp;
    }

    public static String convertObject2String(Object obj) {
        return gson.toJson(obj);
    }

}
