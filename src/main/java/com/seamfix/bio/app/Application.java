/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.app;

import com.seamfix.bio.service.MigrationJobService;
import static java.lang.System.exit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);

    }

    @Override
    public void run(String... args) throws Exception {
        migrationJobService.launchJob();
        exit(0);
    }

}
