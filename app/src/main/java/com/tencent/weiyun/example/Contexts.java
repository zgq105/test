package com.tencent.weiyun.example;

import com.tencent.weiyun.WeiyunSDKContext;

import java.net.Proxy;

public class Contexts extends WeiyunSDKContext {

    public String uid = "";
    public String appID = "";
    public String openID = "";
    public String accessToken = "";

    private static Contexts instance = null;
    private Contexts() {}
    public static Contexts getInstance() {
        if (instance == null) {
            instance = new Contexts();
        }
        return instance;
    }

    @Override
    public String getCachePath() {
        return MainApplication.getContext().getFilesDir().toString();
    }

    @Override
    public String getAppID() {
        return Constants.WY_APP_ID;
    }

    @Override
    public String getUid() {
        return this.uid;
    }

    @Override
    public int getLoginType() {
        return WeiyunLoginTypeWX;
    }

    @Override
    public String getSKEY() {
        return "";
    }

    @Override
    public String getOpenAppID() {
        return this.appID;
    }

    @Override
    public String getOpenID() {
        return this.openID;
    }

    @Override
    public String getAccessToken() {
        return this.accessToken;
    }

    @Override
    public void traceLog(String log, int level) {

    }

    @Override
    public Proxy getProxy() {
        return null;
    }
}
