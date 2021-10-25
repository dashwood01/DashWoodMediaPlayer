package com.dashwood.dashwoodmediaplayer;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.dashwood.dashwoodmediaplayer.handler.HandlerAudioRecorder;
import com.dashwood.dashwoodmediaplayer.handler.HandlerCheckerValue;
import com.dashwood.dashwoodmediaplayer.listener.CallBackDownloader;
import com.dashwood.dashwoodmediaplayer.server.DownloadHttpService;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class DashWoodMediaPlayer {
    private String filePath;
    private HandlerAudioRecorder handlerAudioRecorder;
    private CallBackDownloader callBackDownloader;
    private RequestQueue requestQueue;

    public DashWoodMediaPlayer setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public DashWoodMediaPlayer downloadMediaFile(String link, Context context) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }
        connectServerDownloadMediaFile(link);
        return this;
    }

    public DashWoodMediaPlayer setCallBackDownloader(CallBackDownloader callBackDownloader) {
        this.callBackDownloader = callBackDownloader;
        return this;
    }

    public void startPlay() throws IOException {
        startPlaying();
    }

    public void stopPlay() {
        stopPlaying();
    }


    private void startPlaying() throws IOException {
        if (HandlerCheckerValue.checkEmptyOrNullValue(filePath)) {
            throw new IllegalArgumentException("You must first set the filePath");
        }
        handlerAudioRecorder = new HandlerAudioRecorder("");
        handlerAudioRecorder.play(filePath);
    }

    private void stopPlaying() {
        if (handlerAudioRecorder == null) {
            throw new IllegalArgumentException("You must first start Playing");
        }
        handlerAudioRecorder.stopPlaying();
    }

    public int getDuration() {
        return handlerAudioRecorder.getDuration();
    }

    public int getCurrentPosition() {
        return handlerAudioRecorder.getCurrentPosition();
    }

    public void setSeekTo(int position) {
        if (handlerAudioRecorder == null) {
            throw new IllegalArgumentException("You must first start Playing");
        }
        handlerAudioRecorder.setSeekToPlaying(position);
    }

    private void connectServerDownloadMediaFile(String link) {
        DownloadHttpService downloadHttpService = new DownloadHttpService(link,
                response -> {
                    if (callBackDownloader != null) {
                        callBackDownloader.onSuccess(response);
                    }
                    if (!HandlerCheckerValue.checkEmptyOrNullValue(filePath)) {
                        throw new IllegalArgumentException("filePath is null then media file not created");
                    }
                    try {
                        FileOutputStream fileStream = new FileOutputStream(filePath);
                        fileStream.write(response);
                        fileStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            if (callBackDownloader != null) {
                callBackDownloader.onError(error);
                return;
            }
            NetworkResponse networkResponse = error.networkResponse;
            if (networkResponse == null) {
                throw new IllegalArgumentException("Error of volley is null");
            }
            Log.e("ErrorDashWood", "Error code : " +
                    networkResponse.statusCode + "\nError : " +
                    Arrays.toString(networkResponse.data));
        });
        requestQueue.add(downloadHttpService);
    }
}
