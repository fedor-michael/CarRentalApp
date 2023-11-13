package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class ThreadPoolConfiguration {

    @Bean
    public Executor importExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        executor.setQueueCapacity(10);
        executor.setThreadNamePrefix("entity-import-");
        executor.initialize();
        return executor;
    }

//    @Bean
//    public Executor rentImportExecutor() {
//        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(1);
//        executor.setMaxPoolSize(1);
//        executor.setQueueCapacity(10);
//        executor.setThreadNamePrefix("rent-import-");
//        executor.initialize();
//        return executor;
//    }
//
//    @Bean
//    public Executor userImportExecutor() {
//        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(1);
//        executor.setMaxPoolSize(1);
//        executor.setQueueCapacity(10);
//        executor.setThreadNamePrefix("user-import-");
//        executor.initialize();
//        return executor;
//    }

    @Bean
    public Executor processUpdateExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("process-update-");
        executor.initialize();
        return executor;
    }

}
