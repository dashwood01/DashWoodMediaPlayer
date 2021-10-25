package com.dashwood.dashwoodmediaplayer.listener;
public interface CallBackDownloader {
    void onSuccess(byte[] response);

    void onError(Exception e);
}
