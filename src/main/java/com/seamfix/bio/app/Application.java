/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.app;

import com.seamfix.bio.mongodb.datasource.MongoDbDataSource;
import com.seamfix.bio.service.FingerPrintConversionJobSerive;
import com.seamfix.bio.service.MigrationJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import static java.lang.System.exit;

/**
 *
 * @author Uchechukwu Onuoha
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.seamfix.bio")
public class Application implements CommandLineRunner {

    @Autowired
    MigrationJobService migrationJobService;

    @Autowired
    FingerPrintConversionJobSerive fingerPrintConversionJobSerive;

    @Autowired
    MongoDbDataSource mongoDbDataSource;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);

    }

    @Override
    public void run(String... args) throws Exception {
        //migrationJobService.launchJob();
        fingerPrintConversionJobSerive.launchJob();
        exit(0);
    }

}
