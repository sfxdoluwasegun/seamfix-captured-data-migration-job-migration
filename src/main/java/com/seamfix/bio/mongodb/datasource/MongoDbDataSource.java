/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.mongodb.datasource;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import com.seamfix.bio.util.CryptManager;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.keyczar.Crypter;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import nw.commons.logging.Loggable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author Godswill
 */
@Component
public class MongoDbDataSource extends Loggable {

    private MongoClient mongoClient;

    protected static final String PLUS = "&";

    private static final Logger log = LoggerFactory.getLogger(MongoDbDataSource.class);

    @Value("${mongo-admindb}")
    private String adminDb;

    @Value("${mongo-dbname}")
    private String appDbName;

    @Value("${mongo-user}")
    private String user;
    @Value("${mongo-pw}")
    private String pw;
    @Value("${mongo-host}")
    private String host;
    @Value("${mongo-port}")
    private String port;

    @Value("${mongo-connectionuri}")
    private String connectionUrl;
    @Value("${mongo-usereplica}")
    private String useReplica;
    @Value("${mongo-connectionsPerHost}")
    private String connectionsPerHost;
    @Value("${mongo-connectTimeout}")
    private String connectTimeout;
    @Value("${mongo-cursorFinalizerEnabled}")
    private String cursorFinalizerEnabled;
    @Value("${mongo-maxWaitTime}")
    private String maxWaitTime;
    @Value("${mongo-socketTimeout}")
    private String socketTimeout;
    @Value("${mongo-modelregistry}")
    private String modelRegistry;
    @Value("${mongo-ssl}")
    private String useSsl;
    @Value("${mongo-replicaset}")
    private String replicaSet;
    @Value("${mongo-authsource}")
    private String authSource;
    @Value("${mongo-authmechanism}")
    private String authMechanism;

    private String sslValue = "";
    private String replicaSetValue = "";
    private String authMechanismValue = "";
    private String authSourceValue = "";

    @Autowired
    CryptManager cryptManager;

    @PostConstruct
    public void init() {
        try {
            sslValue = "ssl=" + useSsl;
            replicaSetValue = "replicaSet=" + replicaSet;
            authMechanismValue = "authMechanism=" + authMechanism;
            authSourceValue = "authSource=" + authSource;
            mongoClient = getAppMogoDbClient();
            log.info("Sucessful connection :");
        } catch (UnsupportedEncodingException e) {
            log.error("Error :" + e);
        }

    }

    private MongoClientOptions getMongoOptions() {
        MongoClientOptions.Builder optionsBuilder = MongoClientOptions.builder();
        optionsBuilder.connectionsPerHost(new Integer(connectionsPerHost));
        optionsBuilder.connectTimeout(new Integer(connectTimeout));
        optionsBuilder.cursorFinalizerEnabled(Boolean.valueOf(cursorFinalizerEnabled));
        optionsBuilder.maxWaitTime(new Integer(maxWaitTime));
        optionsBuilder.socketTimeout(new Integer(socketTimeout));
        return optionsBuilder.build();
    }

    @PreDestroy
    public void stop() {
        if (mongoClient != null) {
            mongoClient.close();
        }

    }

    public MongoDatabase getDatabaseConnection() {
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().register(modelRegistry).automatic(true).build()));
        return mongoClient.getDatabase(appDbName).withCodecRegistry(pojoCodecRegistry);
    }

    public MongoClient getMogoDbClient() {
        return mongoClient;
    }

    private MongoClient getAppMogoDbClient() throws UnsupportedEncodingException {
        MongoClient innerMongoClient = null;
        String plainUsername = null;
        String plainPw = null;
        Crypter crypter = null;
        String username = user;
        String password = pw;
        if (username != null && password != null) {
            try {
                crypter = cryptManager.getCrypter();
                plainUsername = crypter.decrypt(username);
                plainPw = crypter.decrypt(password);

            } catch (Exception ke) {
                logger.error("Error  " + ke.getMessage());
                logger.error("Error in decrypting mongo db credentials");
                logger.error("using default mongo db credentials");
                plainUsername = username;
                plainPw = password;
            }
        } else {
            plainUsername = username;
            plainPw = password;
        }

        String dbName = adminDb;
        String replicaConnectionUrl = connectionUrl;
        String mongoHost = host;
        int mongoPort = new Integer(port);
        if (Boolean.valueOf(useReplica)) {
            replicaConnectionUrl = replicaConnectionUrl + sslValue;
            replicaConnectionUrl = replicaConnectionUrl + PLUS + replicaSetValue;
            replicaConnectionUrl = replicaConnectionUrl + PLUS + authSourceValue;
            replicaConnectionUrl = replicaConnectionUrl + PLUS + authMechanismValue;
            //connect to mongodb replica set
            StringBuilder uriBuilder = new StringBuilder("mongodb://");

            String credentials = plainUsername + ":" + URLEncoder.encode(plainPw, "UTF-8") + "@";
            uriBuilder.append(credentials).append(replicaConnectionUrl);

            innerMongoClient = new MongoClient(new MongoClientURI(uriBuilder.toString()));
        } else {
            MongoCredential credential = MongoCredential.createCredential(plainUsername, dbName, plainPw.toCharArray());
            if (plainUsername.trim().isEmpty() || plainPw.trim().isEmpty()) {
                innerMongoClient = new MongoClient(mongoHost, mongoPort);
            } else {
                innerMongoClient = new MongoClient(new ServerAddress(mongoHost, mongoPort), credential, getMongoOptions());
            }
        }
        return innerMongoClient;
    }

    public String getAppDbName() {
        return appDbName;
    }

}
