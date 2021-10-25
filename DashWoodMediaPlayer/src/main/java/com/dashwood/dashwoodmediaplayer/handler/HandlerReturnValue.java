package com.dashwood.dashwoodmediaplayer.handler;

public class HandlerReturnValue {
    public static String getPathWithoutSlashChar(String path) {
        if (path != null && path.length() > 0 && path.charAt(path.length() - 1) == '/') {
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }
}
