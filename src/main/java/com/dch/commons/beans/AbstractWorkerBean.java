package com.dch.commons.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by dcherdyntsev on 11.08.2015.
 */
public abstract class AbstractWorkerBean<T> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractWorkerBean.class);

    protected volatile boolean workersRun = true;

    protected Map<Future, AtomicBoolean> workersRunMap = new HashMap<>();

    @PostConstruct
    @Lock(LockType.WRITE)
    protected void init() throws Exception {
        checkRunScheduler();
    }

    protected abstract ExecutorService getExecutorService();

    @Schedule(persistent = false, second = "*/10", minute = "*", hour = "*", info = "Worker check run scheduler")
    protected void checkRunScheduler() throws Exception {
        reloadProperties();

        if(!workersRun)
            return;
        List<Future> remove = new ArrayList<>();
        for(Future f : workersRunMap.keySet()) {
            if(f.isDone()) {
                remove.add(f);
            }
        }
        for(Future f : remove) {
            workersRunMap.remove(f);
            try {
                f.get();
            } catch(Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        for(int i= workersRunMap.size(); i<getThreadsCount(); i++) {
            T t = doBefore();
            final AtomicBoolean workerRun = new AtomicBoolean(true);
            Future f = getExecutorService().submit(() -> {
                doWork(t, workerRun);
                return null;
            });
            workersRunMap.put(f, workerRun);
        }

        while (workersRunMap.size() > getThreadsCount()) {
            Map.Entry<Future, AtomicBoolean> entry = workersRunMap.entrySet().iterator().next();
            entry.getValue().set(false);
            workersRunMap.remove(entry.getKey());
        }
    }

    protected void reloadProperties(){

    };

    protected abstract T doBefore() throws Exception;

    protected abstract int getThreadsCount();

    protected abstract void doWork(T t, AtomicBoolean workerRun) throws Exception;

    public void stop() {
        workersRun = false;
    }

    @PreDestroy
    protected void destroy() throws IOException {
        stop();
    }

}
