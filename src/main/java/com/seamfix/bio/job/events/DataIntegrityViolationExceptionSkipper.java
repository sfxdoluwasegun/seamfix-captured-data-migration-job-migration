/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.job.events;

import org.springframework.batch.core.step.skip.SkipPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *
 * @author Uchechukwu Onuoha
 */
public class DataIntegrityViolationExceptionSkipper implements SkipPolicy {

    private static final Logger logger = LoggerFactory.getLogger("DataIntegrityViolationException");

    @Override
    public boolean shouldSkip(Throwable exception, int skipCount) {
        if (exception instanceof DataIntegrityViolationException) {
            DataIntegrityViolationException ex = (DataIntegrityViolationException) exception;
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("An error occured while processing the " + ex.getClass().getSimpleName());
            errorMessage.append("Error :" + ex.getMessage());
            logger.error("{}", errorMessage.toString());
            return true;
        } else {
            return false;
        }
    }

}
