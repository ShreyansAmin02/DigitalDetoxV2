package com.example.digitaldetox.tracker;

public class screentime_tracker {
    //https://stackoverflow.com/questions/10820033/make-a-simple-timer-in-java
    long startTime;
    long totalTime;
    long sessionTime;
    public screentime_tracker() {
        startTime = 0;
        // total time initalised at 0 for now, but plans to store total time in database and so total time is
        // not reset to 0 when initialised.
        totalTime = 0;
        sessionTime = 0;
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
    public String getTime() {
        long elapsedSeconds = totalTime / 1000;
        long secondsDisplay = elapsedSeconds % 60;
        long elapsedMinutes = elapsedSeconds / 60;
        long minutesDisplay = elapsedMinutes % 60;
        long elapsedHours = elapsedMinutes / 60;
        long hoursDisplay = elapsedHours % 60;

        return (String.format("%02d:%02d:%02d", hoursDisplay, minutesDisplay, secondsDisplay));
    }

    public static void main(String[] args) {
        screentime_tracker tracker = new screentime_tracker();
        tracker.startTracker();
        try {
            Thread.sleep(5000); // Simulate 5 seconds of usage
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tracker.stopTracker();


        System.out.println("Total Usage Time (Formatted): " + tracker.getTime());
        System.out.println("Session Usage Time (Formatted): " + tracker.getTime());
    }

}
