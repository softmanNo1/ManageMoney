package com.money.util;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AppUtil {
    /** Network type is unknown */
    public static final int NETWORK_TYPE_UNKNOWN = 0;
    /** Current network is GPRS */
    public static final int NETWORK_TYPE_GPRS = 1;
    /** Current network is EDGE */
    public static final int NETWORK_TYPE_EDGE = 2;
    /** Current network is UMTS */
    public static final int NETWORK_TYPE_UMTS = 3;
    /** Current network is CDMA: Either IS95A or IS95B*/
    public static final int NETWORK_TYPE_CDMA = 4;
    /** Current network is EVDO revision 0*/
    public static final int NETWORK_TYPE_EVDO_0 = 5;
    /** Current network is EVDO revision A*/
    public static final int NETWORK_TYPE_EVDO_A = 6;
    /** Current network is 1xRTT*/
    public static final int NETWORK_TYPE_1xRTT = 7;
    /** Current network is HSDPA */
    public static final int NETWORK_TYPE_HSDPA = 8;
    /** Current network is HSUPA */
    public static final int NETWORK_TYPE_HSUPA = 9;
    /** Current network is HSPA */
    public static final int NETWORK_TYPE_HSPA = 10;
    /** Current network is iDen */
    public static final int NETWORK_TYPE_IDEN = 11;
    /** Current network is EVDO revision B*/
    public static final int NETWORK_TYPE_EVDO_B = 12;
    /** Current network is LTE */
    public static final int NETWORK_TYPE_LTE = 13;
    /** Current network is eHRPD */
    public static final int NETWORK_TYPE_EHRPD = 14;
    /** Current network is HSPA+ */
    public static final int NETWORK_TYPE_HSPAP = 15;


    private static final int NETWORK_TYPE_UNAVAILABLE = -1;
    // private static final int NETWORK_TYPE_MOBILE = -100;
    private static final int NETWORK_TYPE_WIFI = -101;

    private static final int NETWORK_CLASS_WIFI = -101;
    private static final int NETWORK_CLASS_UNAVAILABLE = -1;
    /** Unknown network class. */
    private static final int NETWORK_CLASS_UNKNOWN = 0;
    /** Class of broadly defined "2G" networks. */
    private static final int NETWORK_CLASS_2_G = 1;
    /** Class of broadly defined "3G" networks. */
    private static final int NETWORK_CLASS_3_G = 2;
    /** Class of broadly defined "4G" networks. */
    private static final int NETWORK_CLASS_4_G = 3;

    /** Latitude**/
    public static final int LOCATION_LATITUDE = 0;
    /**ongitude**/
    public static final int LOCATION_LONGITUDE = 1;

    /**
     * 获取网络类型 2g 3g 4g  wifi
     * @param context
     * @return
     */
    public static String getCurrentClassNetworkType(Context context) {
        int networkClass = getNetworkClass(context);
        String type = "未知";
        switch (networkClass) {
            case NETWORK_CLASS_UNAVAILABLE:
                type = "无";
                break;
            case NETWORK_CLASS_WIFI:
                type = "Wi-Fi";
                break;
            case NETWORK_CLASS_2_G:
                type = "2G";
                break;
            case NETWORK_CLASS_3_G:
                type = "3G";
                break;
            case NETWORK_CLASS_4_G:
                type = "4G";
                break;
            case NETWORK_CLASS_UNKNOWN:
                type = "未知";
                break;
        }
        return type;
    }

    private static int getNetworkClass(Context context) {
        int networkType = NETWORK_TYPE_UNKNOWN;
        try {
            ConnectivityManager mConnection = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo network = mConnection.getActiveNetworkInfo();
            if (network != null && network.isAvailable()
                    && network.isConnected()) {
                int type = network.getType();
                if (type == ConnectivityManager.TYPE_WIFI) {
                    networkType = NETWORK_TYPE_WIFI;
                } else if (type == ConnectivityManager.TYPE_MOBILE) {
                    TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
                    networkType = telephonyManager.getNetworkType();
                }
            } else {
                networkType = NETWORK_TYPE_UNAVAILABLE;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return getNetworkClassByType(networkType);

    }

    private static int getNetworkClassByType(int networkType) {
        switch (networkType) {
            case NETWORK_TYPE_UNAVAILABLE:
                return NETWORK_CLASS_UNAVAILABLE;
            case NETWORK_TYPE_WIFI:
                return NETWORK_CLASS_WIFI;
            case NETWORK_TYPE_GPRS:
            case NETWORK_TYPE_EDGE:
            case NETWORK_TYPE_CDMA:
            case NETWORK_TYPE_1xRTT:
            case NETWORK_TYPE_IDEN:
                return NETWORK_CLASS_2_G;
            case NETWORK_TYPE_UMTS:
            case NETWORK_TYPE_EVDO_0:
            case NETWORK_TYPE_EVDO_A:
            case NETWORK_TYPE_HSDPA:
            case NETWORK_TYPE_HSUPA:
            case NETWORK_TYPE_HSPA:
            case NETWORK_TYPE_EVDO_B:
            case NETWORK_TYPE_EHRPD:
            case NETWORK_TYPE_HSPAP:
                return NETWORK_CLASS_3_G;
            case NETWORK_TYPE_LTE:
                return NETWORK_CLASS_4_G;
            default:
                return NETWORK_CLASS_UNKNOWN;
        }
    }

    /**
     * 获取网络类型
     * @param mcContext
     * @return
     */
    public static String getNetWorkType(Context mcContext){
        ConnectivityManager mConnection = (ConnectivityManager)mcContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = mConnection.getActiveNetworkInfo();
        if (network != null && network.isAvailable()
                && network.isConnected()) {
            int type = network.getType();
            if (type == ConnectivityManager.TYPE_WIFI) {
                return "WIFI";
            }else{
                return "MOBILE";
            }
        }
        return "";
    }
    /**
     * 获取设备Id
     * @param mContext
     * @return
     */
    public static String getDeviceId(Context mContext) {
        try {
            // 1 compute IMEI
            TelephonyManager TelephonyMgr = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);

            String imei = null;
            if (TelephonyMgr != null) {
                imei = TelephonyMgr.getDeviceId(); // Requires READ_PHONE_STATE
            }
            if (!TextUtils.isEmpty(imei)) {
                // got imei, return it
                return imei.trim();
            }

            // 2 compute DEVICE ID
            String devIDShort = "35"
                    + // we make this look like a valid IMEI
                    Build.BOARD.length() % 10 + Build.BRAND.length() % 10
                    + Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10
                    + Build.DISPLAY.length() % 10 + Build.HOST.length() % 10
                    + Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10
                    + Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10
                    + Build.TAGS.length() % 10 + Build.TYPE.length() % 10
                    + Build.USER.length() % 10; // 13 digits

            // 3 android ID - unreliable
            String androidId = Settings.Secure.getString(
                    mContext.getContentResolver(), Settings.Secure.ANDROID_ID);

            // 4 wifi manager read MAC address - requires
            // android.permission.ACCESS_WIFI_STATE or comes as null
            WifiManager wm = (WifiManager) mContext
                    .getSystemService(Context.WIFI_SERVICE);
            String wlanMac = null;
            if (wm != null) {
                try {
                    wlanMac = wm.getConnectionInfo().getMacAddress();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // 5 Bluetooth MAC address android.permission.BLUETOOTH required, so
            // currenty just comment out, in case we use this method later
            String btMac = null;

            /*
             * BluetoothAdapter bluetoothAdapter = null; // Local Bluetooth
             * adapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
             * if (bluetoothAdapter != null) { try { btMac =
             * bluetoothAdapter.getAddress(); } catch (Exception e) {
             * e.printStackTrace(); } }
             */

            // 6 SUM THE IDs
            String devIdLong = imei + devIDShort + androidId + wlanMac + btMac;
            MessageDigest m = null;
            try {
                m = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            m.update(devIdLong.getBytes(), 0, devIdLong.length());

            byte md5Data[] = m.digest();

            String uniqueId = "";
            for (int i = 0; i < md5Data.length; i++) {
                int b = (0xFF & md5Data[i]);
                // if it is a single digit, make sure it have 0 in front (proper
                // padding)
                if (b <= 0xF)
                    uniqueId += "0";
                // add number to string
                uniqueId += Integer.toHexString(b);
            }
            uniqueId = uniqueId.toUpperCase();
            if (uniqueId.length() > 15) {
                uniqueId = uniqueId.substring(0, 15);
            }
            return uniqueId.trim();
        } catch (Exception e) {
        }
        return "DeviceId0";
    }

    /**
     * 版本name
     * @param mContext
     * @return
     */
    public static String getVersionName(Context mContext) {
        String versionName = "";
        try {
            PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            versionName = packageInfo.versionName;
        } catch (Exception e) {
        }
        return versionName;
    }

    /**
     * 版本code
     * @param mContext
     * @return
     */
    public static String getVersionCode(Context mContext) {
        int versionCode = 0;
        try {
            PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            versionCode = packageInfo.versionCode;
        } catch (Exception e) {
        }
        return String.valueOf(versionCode);
    }

    /**
     * 获取应用名称
     * @param mContext
     * @return
     */
    public static String getApplicationName(Context mContext) {
        String appName = "";
        try {
            ApplicationInfo ai = mContext.getPackageManager().getApplicationInfo(mContext.getPackageName(),
                    PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            appName = bundle.getString("APP_NAME");
        } catch (Exception e) {
        }
        return appName;
    }

    /**
     * 运营商代号
     * @param mContext
     * @return
     */
     public static String getMccmnc(Context mContext){

        TelephonyManager mTelephonyManager = null;
        mTelephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        if(mTelephonyManager.getSimOperator() != null){
            return mTelephonyManager.getSimOperator();
        }else{
            return "";
        }
    }

    /**
     * 获取经纬度信息
     * @param mContext
     * @param locationType
     * @return
     */
    public static double getLocation(Context mContext,int locationType) {
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                if(locationType == LOCATION_LATITUDE){
                   return location.getLatitude();
                }
                if(locationType ==  LOCATION_LONGITUDE){
                    return location.getLongitude();
                }
            }
        }
        return 0;
    }
}
