package com.example.oneul.global.util;

import java.util.HashMap;
import java.util.Map;

import com.example.oneul.global.config.BatchConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BatchScheduler {
    private final Logger log = LoggerFactory.getLogger(BatchScheduler.class);

    private JobLauncher jobLauncher;
    private BatchConfig batchConfig;

    public BatchScheduler(JobLauncher jobLauncher, BatchConfig batchConfig){
        this.jobLauncher = jobLauncher;
        this.batchConfig = batchConfig;
    }

    // 1 분마다 실행
    @Scheduled(cron = "0 * * * * *")
    public void runjob(){
        Map<String, JobParameter> configMap = new HashMap<>();
        configMap.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(configMap);
        
        try {
            jobLauncher.run(batchConfig.job(), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException | JobParametersInvalidException | JobRestartException e) {
            log.error(e.getMessage());
        }
    }
}
