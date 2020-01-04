package com.dandinglong.config;

import org.springframework.beans.factory.annotation.Value;
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
    public ThreadPoolExecutor getPool(@Value("${threadPoolCoreSize}" )int coresize,@Value("${threadPoolMaxSize}" )int maxSize) {
        BlockingQueue<Runnable> queue = getQueue();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(coresize, maxSize, 5, TimeUnit.MINUTES, queue);
        return threadPoolExecutor;
    }
}
