package com.example.digitaldetox.model.Screen_Tracker;

public class screentime_tracker {
    //https://stackoverflow.com/questions/10820033/make-a-simple-timer-in-java
    boolean isTrackerStarted;
    long startTime;
    long totalTime;
    long sessionTime;
    public screentime_tracker() {
        totalTime = 0;
        sessionTime = 0;
        isTrackerStarted = false;
    }

    public void reset() {
        if (isTrackerStarted) {
            stopTracker();
        }
        totalTime = 0;
        sessionTime = 0;
        startTracker();
    }

    public long getTotalTime() {
        stopTracker();
        startTracker();
        return totalTime;
    }

    public void startTracker() {
        startTime = System.currentTimeMillis();
        isTrackerStarted = true;
        sessionTime = 0;
    }
    public void stopTracker() {
        long stopTime = System.currentTimeMillis();
        sessionTime = stopTime - startTime;
        totalTime += sessionTime;
        isTrackerStarted = false;
    }

}