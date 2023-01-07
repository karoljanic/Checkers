package org.checkers.utils;

/**
 * klasa służy jako przerywnik programu do czekania na odpowiedź klienta
 */
public class CustomClock {
    /**
     * @param time ilość milisekund, którą trzeba czekać
     * funkcja realizująca czekanie
     */
    public static void waitMillis(int time) {
        long startTime = System.currentTimeMillis();
        long currentTime = startTime;

        while(currentTime - startTime < time) {
            currentTime = System.currentTimeMillis();
        }
    }
}
