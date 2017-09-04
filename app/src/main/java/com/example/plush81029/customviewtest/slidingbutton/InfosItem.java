//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.plush81029.customviewtest.slidingbutton;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.Base64;

public class InfosItem {
    private String type = "0";
    private String appCode;
    private String screenRes;
    private String memory;
    private String availableSpace;
    private String systemTime;
    private String bootTime;
    private String trueIp;
    private String osVersion;
    private String imei;

    public String getAvailableSpace() {
        return this.availableSpace;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAppCode() {
        return this.appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getScreenRes() {
        return this.screenRes;
    }

    public void setScreenRes(String screenRes) {
        this.screenRes = screenRes;
    }

    public String getMemory() {
        return this.memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getSystemTime() {
        return this.systemTime;
    }

    public void setSystemTime(String systemTime) {
        this.systemTime = systemTime;
    }

    public String getBootTime() {
        return this.bootTime;
    }

    public void setBootTime(String bootTime) {
        this.bootTime = bootTime;
    }

    public String getTrueIp() {
        return this.trueIp;
    }

    public void setTrueIp(String trueIp) {
        this.trueIp = trueIp;
    }

    public String getOsVersion() {
        return this.osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getImei() {
        return this.imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public void setAvailableSpace(String availableSpace) {
        this.availableSpace = availableSpace;
    }

    public InfosItem(String appCode, Context context) {
        this.appCode = appCode;
        this.screenRes = Utils.getScreenDisplay(context);
        this.memory = "-";
        this.availableSpace = "-";
        this.bootTime = "-";
        this.trueIp = Utils.getLocalIpAddress(context);
        this.osVersion = VERSION.RELEASE;
        this.imei = Utils.getPhoneImei(context);
    }

    public String generateInfoMd5String() {
        this.systemTime = System.currentTimeMillis() + "";
        String sourceStr = this.type + "^" + this.appCode + "^" + this.screenRes + "^" + this.memory + "^" + this.availableSpace + "^" + this.systemTime + "^" + this.bootTime + "^" + this.trueIp + "^" + this.osVersion + "^" + this.imei;
        String md5Str = Utils.MD5(sourceStr + "slide");
        String result = md5Str + "_" + sourceStr;
        return new String(Base64.encode(result.getBytes(), 0));
    }
}
