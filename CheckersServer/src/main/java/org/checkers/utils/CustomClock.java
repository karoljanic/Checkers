package org.checkers.utils;

public class CustomClock {
    public static void waitMillis(int time) {
        long startTime = System.currentTimeMillis();
        long currentTime = startTime;

        while(currentTime - startTime < time) {
            currentTime = System.currentTimeMillis();
        }
    }
}
