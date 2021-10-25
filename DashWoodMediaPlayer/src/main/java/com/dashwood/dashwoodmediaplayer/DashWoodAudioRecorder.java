package com.dashwood.dashwoodmediaplayer;


import android.content.Context;
import android.util.Log;

import com.dashwood.dashwoodmediaplayer.handler.HandlerAudioRecorder;
import com.dashwood.dashwoodmediaplayer.handler.HandlerCheckerValue;
import com.dashwood.dashwoodmediaplayer.handler.HandlerReturnValue;

import java.io.File;
import java.io.IOException;

public class DashWoodAudioRecorder {
    private String path, fileName;
    private HandlerAudioRecorder handlerAudioRecorder;
    private final Context context;

    public DashWoodAudioRecorder(Context context) {
        this.context = context;
    }

    public DashWoodAudioRecorder setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public DashWoodAudioRecorder setPath(String path) {
        this.path = path;
        File directory = new File(path);
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IllegalArgumentException("Path could not be created.");
        }
        return this;
    }


    public void startRecord() throws IOException {
        startRecording();
    }

    public void stopRecord() {
        stopRecording();
    }

    private void startRecording() throws IOException {
        if (HandlerCheckerValue.checkEmptyOrNullValue(path)) {
            path = context.getExternalFilesDir("file_record").getPath();
            Log.i("DashWoodAudioRecorder", "Custom path is set, The path is : " + path);
        }
        if (HandlerCheckerValue.checkEmptyOrNullValue(fileName)) {
            fileName = String.valueOf(System.currentTimeMillis());
            Log.i("DashWoodAudioRecorder", "Custom fileName is set, The file name is : " + fileName);
        }
        handlerAudioRecorder = new HandlerAudioRecorder(HandlerReturnValue.getPathWithoutSlashChar(path) + "/" + fileName);
        handlerAudioRecorder.start();
    }

    private void stopRecording() {
        if (handlerAudioRecorder == null) {
            throw new IllegalArgumentException("You must first start Recording");
        }
        handlerAudioRecorder.stop();
    }

    public boolean deleteFile() {
        return new File(HandlerReturnValue.getPathWithoutSlashChar(path) + "/" + fileName).delete();
    }

    public String getFilePath() {
        return HandlerReturnValue.getPathWithoutSlashChar(path) + "/" + fileName;
    }
}
