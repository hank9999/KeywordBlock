package com.github.hank9999.keywordblock.Libs;

import java.util.HashMap;
import java.util.Map;

public class timesCounter {
    private static class Counter {
        int times;
        long sayTime;

        public Counter(int times, long sayTime) {
            this.times = times;
            this.sayTime = sayTime;
        }
    }

    private static final Map<String, Counter> timesCounters = new HashMap<>();

    public static Boolean check(String username) {
        if (timesCounters.get(username).times >= Config.mute.times) {
            timesCounters.remove(username);
            return true;
        } else {
            return false;
        }
    }

    public static void add(String username, int times) {
        Counter count = timesCounters.get(username);
        long nowTime = System.currentTimeMillis() / 1000;
        if (count == null) {
            timesCounters.put(username, new Counter(times, nowTime));
            return;
        }
        if (nowTime - count.sayTime <= Config.mute.keeptime) {
            count.times += times;
        } else {
            count.times = times;
            count.sayTime = nowTime;
        }
        timesCounters.put(username, count);
    }
}
