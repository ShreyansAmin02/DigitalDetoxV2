package com.example.digitaldetox.tracker;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
public class app_tracker {
    public String currentWindow;
    long startTime;
    long totalTime;
    public long sessionTime;
    
    public long facebookTime;
    public long instagramTime;
    public long youtubeTime;
    public app_tracker() {
        this.startTime = 0;
        this.sessionTime = 0;
        this.totalTime = 0;
        this.facebookTime = 0;
        this.instagramTime = 0;
        this.youtubeTime = 0;
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


