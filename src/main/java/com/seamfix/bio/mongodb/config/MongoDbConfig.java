/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.mongodb.config;

import com.mongodb.MongoClient;
import com.seamfix.bio.mongodb.datasource.MongoDbDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 *
 * @author Uchechukwu Onuoha 
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.seamfix.bio.mongodb.dao")
public class MongoDbConfig extends AbstractMongoConfiguration {

    @Autowired
    public MongoDbDataSource mongoDbDataSource;

    @Override
    public MongoClient mongoClient() {
        return mongoDbDataSource.getMogoDbClient();
    }

    @Bean
    @Override
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), getDatabaseName());
    }

    @Override
    protected String getDatabaseName() {
        return mongoDbDataSource.getAppDbName();
    }

}
