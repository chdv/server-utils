package com.dch.commons.utils;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

/**
 * Created by dcherdyntsev on 21.07.2015.
 */
public class ConsumeChecker {

    private long everyCheck;
    private volatile Consumer<Long> consumer;
    private volatile AtomicLong counter = new AtomicLong();
    private ReentrantLock lock = new ReentrantLock();

    public ConsumeChecker(long everyCheck, Consumer<Long> consumer) {
        this.everyCheck = everyCheck;
        this.consumer = consumer;
    }

    public void consumeIfNeed() {
        if(counter.get() >= everyCheck ) {
            lock.lock();
            if(counter.get() >= everyCheck ) {
                consumer.accept(counter.get());
                counter.set(0);
            }
            lock.unlock();
        } else {
            counter.incrementAndGet();
        }
    }

    public static ConsumeChecker empty() {
        return new ConsumeChecker(0, null) {
            @Override
            public void consumeIfNeed() {
                /* NOP */
            }
        };
    }

}
