package com.example.digitaldetox.tracker;

public class screentime_tracker {
    //https://stackoverflow.com/questions/10820033/make-a-simple-timer-in-java
    boolean isTrackerStarted;
    long startTime;
    long totalTime;
    long sessionTime;
    public screentime_tracker() {
        startTime = 0;
        // total time initalised at 0 for now, but plans to store total time in database and so total time is
        // not reset to 0 when initialised.
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
        if (startTime == 0) {
            startTime = System.currentTimeMillis();
        }
        isTrackerStarted = true;
        sessionTime = 0;
    }
    public void stopTracker() {
        long stopTime = System.currentTimeMillis();
        sessionTime = stopTime - startTime;
        totalTime += sessionTime;
        startTime = 0;
        isTrackerStarted = false;
    }
    public String getTime() {
        long elapsedSeconds = totalTime / 1000;
        long secondsDisplay = elapsedSeconds % 60;
        long elapsedMinutes = elapsedSeconds / 60;
        long minutesDisplay = elapsedMinutes % 60;
        long elapsedHours = elapsedMinutes / 60;
        long hoursDisplay = elapsedHours % 60;

        return (String.format("%02d:%02d:%02d", hoursDisplay, minutesDisplay, secondsDisplay));
    }

}
