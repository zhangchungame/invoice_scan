package com.dandinglong.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class DealUploadFileQueueConfig {
    @Bean
    public BlockingQueue<Runnable> getQueue() {
        BlockingQueue<Runnable> queue=new LinkedBlockingQueue<>();
        return queue;
    }

    @Bean
    public ThreadPoolExecutor getPool() {
        BlockingQueue<Runnable> queue = getQueue();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 8, 5, TimeUnit.MINUTES, queue);
        return threadPoolExecutor;
    }
}
