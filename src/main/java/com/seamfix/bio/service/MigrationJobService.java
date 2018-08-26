/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Service
public class MigrationJobService {

    private static final Logger log = LoggerFactory.getLogger(MigrationJobService.class);

    @Autowired
    JobLauncher jobLauncher;

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
