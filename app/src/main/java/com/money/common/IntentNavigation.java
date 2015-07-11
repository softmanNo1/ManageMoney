package com.money.common;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;

import com.money.util.LogUtil;

/**
 * Created by gsf on 15/7/9.
 */
public class IntentNavigation {

    private static String LOG_TAG = IntentNavigation.class.getName();

    public static void startActivity(Context ctx, Intent intent){
        if (intent == null) {
            return;
        }
        try {
            ctx.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            LogUtil.e(LOG_TAG, e.getMessage());
        } catch (SecurityException e) {
            LogUtil.e(LOG_TAG, e.getMessage() );
        }
    }


}
