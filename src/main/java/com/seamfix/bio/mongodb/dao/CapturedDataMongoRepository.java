/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.mongodb.dao;

import com.seamfix.bio.entities.CapturedData;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 *
 * @author Uchechukwu Onuoha
 */
public interface CapturedDataMongoRepository extends MongoRepository<CapturedData, String> {

    @Query("{ 'uniqueId' : ?0 }")
    List<CapturedData> findByUniqueIdQuery(String uniqueId);

}
