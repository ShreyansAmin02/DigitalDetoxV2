package com.example.digitaldetox.tracker;

import com.example.digitaldetox.HelloApplication;
import com.example.digitaldetox.model.UserSession;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
public class app_tracker {
    public String currentWindow;
    public String previousWindow;
    long startTime;
    long totalTime;
    long sessionTime;
    long facebookTime;
    long instagramTime;
    long youtubeTime;
    long tikTokTime;
    long redditTime;
    screentime_tracker overall_tracker;
    public app_tracker() {
        this.startTime = 0;
        this.sessionTime = 0;
        this.totalTime = 0;
        this.facebookTime = 0;
        this.instagramTime = 0;
        this.youtubeTime = 0;
        this.tikTokTime = 0;
        this.redditTime = 0;
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
            // using if statements for now, but could potentially use
            // enums to map names to ints for use of switch statements instead
            while (HelloApplication.isProgramRunning()) {
                startTracker();
                WinDef.HWND viewedWindow = user32.GetForegroundWindow();
                currentWindow = getCurrentWindow(viewedWindow, user32);
                if (!currentWindow.equals(previousWindow)) {
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
                    previousWindow = currentWindow;
                }
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
        this.startTime = 0;
        this.sessionTime = 0;
        this.totalTime = 0;
        this.facebookTime = 0;
        this.instagramTime = 0;
        this.youtubeTime = 0;
        this.tikTokTime = 0;
        this.redditTime = 0;
        overall_tracker.reset();
    }

    public void startTracker() {
        if (startTime == 0) {
            startTime = System.currentTimeMillis();
        }
        sessionTime = 0;
    }
    public void stopTracker() {
        long stopTime = System.currentTimeMillis();
        sessionTime = stopTime - startTime;
        totalTime += sessionTime;
        startTime = 0;
    }
    public String getScreentime(long time) {
        long elapsedSeconds = time / 1000;
        long secondsDisplay = elapsedSeconds % 60;
        long elapsedMinutes = elapsedSeconds / 60;
        long minutesDisplay = elapsedMinutes % 60;
        long elapsedHours = elapsedMinutes / 60;
        long hoursDisplay = elapsedHours % 60;

        return (String.format("%02d:%02d:%02d", hoursDisplay, minutesDisplay, secondsDisplay));
    }
    

    public String getCurrentWindow(WinDef.HWND hwnd, User32 user32) {
        char[] windowText = new char[512];
        user32.GetWindowText(hwnd, windowText, 512);
        return Native.toString(windowText);
    }

}


