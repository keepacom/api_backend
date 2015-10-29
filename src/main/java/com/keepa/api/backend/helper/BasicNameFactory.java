package com.keepa.api.backend.helper;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * HelperFactory to name ThreadPool-Threads for debugging purpose.
 */
public class BasicNameFactory implements ThreadFactory {
    private final String namingPattern;
    final AtomicInteger count = new AtomicInteger(0);

    public BasicNameFactory(String s) {
        this.namingPattern = s;
    }

    @Override
    public Thread newThread(Runnable r) {
        final Thread t = new Thread(r);
        if(this.namingPattern != null) {
            t.setName(String.format(namingPattern, count.getAndIncrement()));
        }

        return t;
    }
}
