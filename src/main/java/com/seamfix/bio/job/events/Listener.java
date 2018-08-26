package com.seamfix.bio.job.events;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
/**
 *
 * @author Uchechukwu Onuoha 
 */
public class Listener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(Listener.class);

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("Finish Job! Check the results");

        }
    }
    
    @Override
    public void beforeJob(JobExecution jobExecution) {
      if (jobExecution.getStatus() == BatchStatus.STARTED) {
            log.info("Started Job! Good to go sir");

        } // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: return
         *  */
        // </editor-fold>
    }
}
