package com.example.digitaldetox.tracker;

import com.example.digitaldetox.HelloApplication;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

public class main {
    public static void main(String[] args) {
        app_tracker tracker = new app_tracker();
        screentime_tracker overall_tracker = new screentime_tracker();
        overall_tracker.startTracker();
        User32 user32 = User32.INSTANCE;


        // using if statements for now, but could potentially use
        // enums to map names to ints for use of switch statements instead
        // while digital detox is running
        while (HelloApplication.isProgramRunning()) {
            WinDef.HWND viewedWindow = user32.GetForegroundWindow();
            tracker.currentWindow = tracker.getCurrentWindow(viewedWindow, user32);
            tracker.startTracker();
            System.out.println("program running");
            if (tracker.currentWindow.contains("Facebook")) {
                tracker.stopTracker();
                tracker.facebookTime += tracker.sessionTime;
                tracker.startTracker();
            } else if (tracker.currentWindow.contains("Instagram")) {
                tracker.stopTracker();
                tracker.instagramTime += tracker.sessionTime;
                tracker.startTracker();
            } else if (tracker.currentWindow.contains("YouTube")) {
                tracker.stopTracker();
                tracker.youtubeTime += tracker.sessionTime;
                tracker.startTracker();
            }
            tracker.stopTracker();
        }

        tracker.stopTracker();
        overall_tracker.stopTracker();


    }
}
