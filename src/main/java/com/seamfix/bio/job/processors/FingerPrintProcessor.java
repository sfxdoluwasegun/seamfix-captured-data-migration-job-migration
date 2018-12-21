package com.seamfix.bio.job.processors;

import com.google.gson.Gson;
import com.mongodb.client.model.Filters;
import com.seamfix.bio.entities.DataUnit;
import com.seamfix.bio.entities.FingerPrint;
import com.seamfix.bio.mongodb.datasource.MongoDbDataSource;
import com.seamfix.bio.util.FingersTools;
import com.sf.plugins.template.extractor.Extractor;
import org.bson.Document;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.logging.Logger;

@Configuration
public class FingerPrintProcessor implements ItemProcessor<FingerPrint,FingerPrint> {

    Logger logger = Logger.getLogger(FingerPrintProcessor.class.getName());

    private MongoDbDataSource mongoDbDataSource;

    public FingerPrintProcessor(MongoDbDataSource mongoDbDataSource) {
        this.mongoDbDataSource = mongoDbDataSource;
    }

    @Value("${fingerPrintsInWsq}")
    private String fingerPrintsInWsq;

    @Value("${mongo-dbname}")
    private String dbName;

    @Override
    public FingerPrint process(FingerPrint fingerPrint) throws Exception {
        FingersTools instance = FingersTools.getInstance();
        Extractor extractor = instance.getExtractor();

        ArrayList<com.seamfix.bio.entities.DataUnit> fingers = fingerPrint.getFingers();
        for(DataUnit dataUnit : fingers){
            logger.info(" ======"+dataUnit.getLabel()+" ====== ");
            dataUnit.setValue(extractor.toWsq(dataUnit.getValue()));
        }

        mongoDbDataSource.getMogoDbClient().getDatabase("bioregistra")
                .getCollection("finger_print_bmp")
                .findOneAndReplace(Filters.eq("uniqueId",fingerPrint.getUniqueId()), Document.parse(new Gson().toJson(fingerPrint)));

        return fingerPrint;
    }
}
