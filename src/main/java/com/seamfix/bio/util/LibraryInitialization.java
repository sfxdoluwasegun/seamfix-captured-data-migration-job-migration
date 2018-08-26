/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.util;

import com.seamfix.util.LibraryManager;
import java.io.IOException;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Component
public class LibraryInitialization {

    private static final Logger logger = LoggerFactory.getLogger(LibraryInitialization.class);

    @Value("${neurotech.server.ip}")
    private String address;
    @Value("${neurotech.server.port}")
    private String port;

    @PostConstruct
    public void init() {
        try {
            LibraryManager.initLibraryPath();
            initFingerLicences();
        } catch (UnsatisfiedLinkError e) {
            logger.error("ERROR: " + e.getMessage());
        }

    }

    private void initFingerLicences() {

        logger.info("===" + "port: " + port + " address: " + address);
        try {
            boolean areObtained = FingersTools.getInstance().obtainLicenses(Arrays.asList("Biometrics.FingerExtraction", "Images.WSQ"), address, Integer.valueOf(port));
            logger.error(areObtained + "========");
        } catch (IOException e) {
            logger.error("==Error obtaining finger conversion licenses: ", e);
        }
    }

}
