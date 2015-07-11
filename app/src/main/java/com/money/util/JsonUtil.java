package com.money.util;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

public class JsonUtil {

    public static JSONObject mapToJSONObject(Map<String, String> map) {
        if (map == null) {
            return null;
        }

        JSONObject jsonObj = new JSONObject();
        try {
            for (String key : map.keySet()) {
                jsonObj.put(key, map.get(key));
            }

        } catch (JSONException e) {

        }

        return jsonObj;
    }

    public static String mapToJSONString(Map<String, String> map) {
        String jsonString = null;
        JSONObject jsonObj = mapToJSONObject(map);
        if (jsonObj != null) {
            jsonString = jsonObj.toString();
        }

        return jsonString;
    }

    public static void copyJSONObject(JSONObject dest, JSONObject src) {
        if (dest == null || src == null) {
            return;
        }

        Iterator<String> keys = src.keys();
        if (keys == null) {
            return;
        }

        try {
            while (keys.hasNext()) {
                String key = keys.next();
                dest.put(key, src.get(key));
            }
        } catch (Exception e) {
        }
    }
}
