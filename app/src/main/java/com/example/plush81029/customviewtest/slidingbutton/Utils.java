//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.plush81029.customviewtest.slidingbutton;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.view.Display;

import java.security.MessageDigest;

public class Utils {
    public Utils() {
    }

    public static String getScreenDisplay(Context context) {
        Display mDisplay = ((Activity)context).getWindowManager().getDefaultDisplay();
        int W = mDisplay.getWidth();
        int H = mDisplay.getHeight();
        String screenDis = W + "," + H;
        return screenDis;
    }

    public static String int2ip(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 255).append(".");
        sb.append(ipInt >> 8 & 255).append(".");
        sb.append(ipInt >> 16 & 255).append(".");
        sb.append(ipInt >> 24 & 255);
        return sb.toString();
    }

    public static final String MD5(String s) {
        char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        try {
            byte[] e = s.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(e);
            byte[] md = mdInst.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;

            for(int i = 0; i < j; ++i) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 15];
                str[k++] = hexDigits[byte0 & 15];
            }

            return new String(str);
        } catch (Exception var10) {
            var10.printStackTrace();
            return null;
        }
    }

    public static String getLocalIpAddress(Context context) {
        try {
            WifiManager ex = (WifiManager)context.getSystemService("wifi");
            WifiInfo wifiInfo = ex.getConnectionInfo();
            int i = wifiInfo.getIpAddress();
            return int2ip(i);
        } catch (Exception var4) {
            return "-";
        }
    }

    public static String getPhoneImei(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService("phone");
        String imei = telephonyManager.getDeviceId();
        return imei;
    }

    public static float getRightFloat(Float f) {
        return (float)Math.round(f.floatValue() * 100.0F) / 100.0F;
    }
}
