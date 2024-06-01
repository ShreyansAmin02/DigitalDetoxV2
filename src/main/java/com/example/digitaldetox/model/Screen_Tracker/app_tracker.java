package com.example.digitaldetox.model.Screen_Tracker;

import com.example.digitaldetox.model.UserSession;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

public class app_tracker {
    String currentWindow;
    long startTime;
    long sessionTime;
    long facebookTime;
    long instagramTime;
    long youtubeTime;
    long tikTokTime;
    long redditTime;
    screentime_tracker overall_tracker;

    public app_tracker() {
        sessionTime = 0;
        facebookTime = 0;
        instagramTime = 0;
        youtubeTime = 0;
        tikTokTime = 0;
        redditTime = 0;
        overall_tracker = new screentime_tracker();
    }

    public long getFacebookTime() {
        return facebookTime;
    }

    public long getInstagramTime() {
        return instagramTime;
    }

    public long getYoutubeTime() {
        return youtubeTime;
    }

    public long getRedditTime() {
        return redditTime;
    }

    public long getTikTokTime() {
        return tikTokTime;
    }

    public void initialise() {
        Thread backgroundThread = new Thread(() -> {
            overall_tracker.startTracker();
            User32 user32 = User32.INSTANCE;
            while (UserSession.getInstance().getLoggedInUser() != null) {
                startTracker();
                WinDef.HWND viewedWindow = user32.GetForegroundWindow();
                currentWindow = getCurrentWindow(viewedWindow, user32);
                if (currentWindow.contains("Facebook")) {
                    stopTracker();
                    facebookTime += sessionTime;
                } else if (currentWindow.contains("Instagram")) {
                    stopTracker();
                    instagramTime += sessionTime;
                } else if (currentWindow.contains("YouTube")) {
                    stopTracker();
                    youtubeTime += sessionTime;
                } else if (currentWindow.contains("TikTok")) {
                    stopTracker();
                    tikTokTime += sessionTime;
                } else if (currentWindow.contains("Reddit")) {
                    stopTracker();
                    redditTime += sessionTime;
                }
                stopTracker();
            }
            stopTracker();
            overall_tracker.stopTracker();
            UserSession.getInstance().clearSession();
        });
        backgroundThread.setDaemon(true);
        backgroundThread.start();
    }

    public void reset() {
        stopTracker();
        sessionTime = 0;
        facebookTime = 0;
        instagramTime = 0;
        youtubeTime = 0;
        tikTokTime = 0;
        redditTime = 0;
        overall_tracker.reset();
    }

    public void startTracker() {
        startTime = System.currentTimeMillis();
        sessionTime = 0;
    }

    public void stopTracker() {
        long stopTime = System.currentTimeMillis();
        sessionTime = stopTime - startTime;
    }

    public String getCurrentWindow(WinDef.HWND hwnd, User32 user32) {
        char[] windowText = new char[512];
        user32.GetWindowText(hwnd, windowText, 512);
        return Native.toString(windowText);
    }

}