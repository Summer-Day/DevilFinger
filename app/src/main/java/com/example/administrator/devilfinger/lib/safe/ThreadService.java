package com.example.administrator.devilfinger.lib.safe;

import com.example.administrator.devilfinger.lib.util.JDBLog;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程调度服务
 *
 * @author liukaixuan
 */
public class ThreadService {

    private static ThreadService _service = new ThreadService();

    public static ThreadService sharedInstance() {
        return _service;
    }

    private final ExecutorService executor;

    private ThreadService() {
        this.executor = Executors.newSingleThreadExecutor();
    }

    /**
     * 将任务提交到线程池执行。任务可能需要排队。
     */
    public void submitTask(Runnable task) {
        try {
            executor.submit(task);
        } catch (Throwable e) {
            JDBLog.detailException(e);
        }
    }

    /**
     * 将任务提交到一个独立的线程执行。任务提交后，不需要排队，直接开启线程执行。
     * <p>
     * 适用于：1. 执行时间特别长的任务；2. 需要立刻执行的任务。
     * </p>
     */
    public void submitTaskInSoloThread(Runnable task) {
        new Thread(task).start();
    }

}
