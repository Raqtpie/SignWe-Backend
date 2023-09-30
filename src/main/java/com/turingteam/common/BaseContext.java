package com.turingteam.common;

public class BaseContext {
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(String id) {
        threadLocal.set(id);
    }

    public static String getCurrentId() {
        return threadLocal.get();
    }
}
