package com.seamfix.bio.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class FingerPrintConversionJobSerive {

    private static final Logger log = LoggerFactory.getLogger(FingerPrintConversionJobSerive.class);

    @Autowired
    JobLauncher jobLauncher;

    @Qualifier(value = "bmpToWsqJob")
    @Autowired
    Job job;

    public void launchJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
            jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            log.error("ERROR : " + e.getMessage());
        }
    }

}
