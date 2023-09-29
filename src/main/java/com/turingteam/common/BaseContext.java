package com.turingteam.common;

public class BaseContext {
    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(int id) {
        threadLocal.set((long) id);
    }

    public static int getCurrentId() {
        return Math.toIntExact(threadLocal.get());
    }
}
