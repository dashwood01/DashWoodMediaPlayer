package com.dashwood.dashwoodmediaplayer.handler;

import android.media.MediaPlayer;
import android.media.MediaRecorder;

import java.io.File;
import java.io.IOException;

public class HandlerAudioRecorder {

    private MediaRecorder recorder;

    public final String filePath;

    public HandlerAudioRecorder(String path) {
        this.filePath = path;
    }

    public void start() throws IOException {
        String state = android.os.Environment.getExternalStorageState();
        if (!state.equals(android.os.Environment.MEDIA_MOUNTED)) {
            throw new IOException("SD Card is not mounted.  It is " + state
                    + ".");
        }
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setOutputFile(filePath);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        recorder.prepare();
        recorder.start();
    }


    public void stop() {
        if (recorder == null) {
            return;
        }
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    private MediaPlayer mp;

    public void play(String path) throws IOException {
        mp = new MediaPlayer();
        mp.setDataSource(path);
        mp.prepare();
        mp.start();
        mp.setVolume(10, 10);
    }

    public void setSeekToPlaying(int msec) {
        if (mp == null) {
            return;
        }
        mp.seekTo(msec);
        mp.start();
    }

    public int getDuration() {
        if (mp == null) {
            return 0;
        }
        return mp.getDuration();
    }

    public int getCurrentPosition() {
        if (mp == null) {
            return 0;
        }
        return mp.getCurrentPosition();
    }

    public void stopPlaying() {
        if (mp == null) {
            return;
        }
        if (mp.isPlaying()) {
            mp.stop();
        }
        if (mp != null) {
            mp = null;
        }
    }
}
