package com.seamfix.bio.mongodb.dao;

import com.seamfix.bio.entities.CapturedData;
import com.seamfix.bio.entities.FingerPrint;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FingerPrintMongoRepository extends MongoRepository<FingerPrint, String> {

}
