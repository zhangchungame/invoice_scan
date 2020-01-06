package com.dandinglong.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class DealUploadFileQueueConfig {
    @Bean
    public BlockingQueue<Runnable> getQueue(@Value("${uploadFileLocation}")String uploadFileLocation,@Value("${excleFileLocation}")String excleFileLocation) {
        File fu=new File(uploadFileLocation);
        fu.mkdirs();
        File fe=new File(excleFileLocation);
        fe.mkdirs();
        BlockingQueue<Runnable> queue=new LinkedBlockingQueue<>();
        return queue;
    }

    @Bean
    public ThreadPoolExecutor getPool() {
        BlockingQueue<Runnable> queue=new LinkedBlockingQueue<>();
        int coreSize=Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(coreSize, coreSize*2, 5, TimeUnit.MINUTES, queue);
        return threadPoolExecutor;
    }
}
