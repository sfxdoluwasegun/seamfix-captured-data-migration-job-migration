package com.seamfix.bio.job.config;

import com.seamfix.bio.job.events.DataIntegrityViolationExceptionSkipper;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import com.seamfix.bio.job.events.Listener;
import com.seamfix.bio.job.events.DuplicateKeyExceptionProcessorSkipper;
import com.seamfix.bio.job.events.NullPointerExceptionSkipper;
import com.seamfix.bio.job.processors.CapturedDataProcessor;
import com.seamfix.bio.entities.CapturedData;
import com.seamfix.bio.mongodb.dao.CapturedDataMongoRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import java.util.HashMap;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public MongoTemplate mongoTemplate;

    @Value("${start}")
    private String start;

    @Value("${end}")
    private String end;

    @Value("${collection.name.to.read.from}")
    private String readFromCollectionName;

    @Value("${collection.name.to.update}")
    private String writeToCollectionName;

    @Autowired
    CapturedDataMongoRepository capturedDataMongoRepository;

    @Bean
    public SkipPolicy duplicateKeyExceptionProcessorSkipper() {
        return new DuplicateKeyExceptionProcessorSkipper();
    }

    @Bean
    public SkipPolicy dataIntegrityViolationExceptionSkipper() {
        return new DataIntegrityViolationExceptionSkipper();
    }

    @Bean
    public SkipPolicy nullPointerExceptionSkipper() {
        return new NullPointerExceptionSkipper();
    }

    @Bean

    public Job job() {
        return jobBuilderFactory.get("job").incrementer(new RunIdIncrementer()).listener(new Listener())
                .flow(capDataStep()).end().build();

    }

    @Bean
    @Qualifier(value = "capDataStep")
    public Step capDataStep() {
        return stepBuilderFactory.get("capDataStep").<CapturedData, CapturedData>chunk(10)
                .reader(capturedDataReader()).processor(new CapturedDataProcessor(capturedDataMongoRepository))./*writer(capturedDataWriter()).faultTolerant().skipPolicy(dataIntegrityViolationExceptionSkipper()).faultTolerant().skipPolicy(duplicateKeyExceptionProcessorSkipper())*/build();
    }

    @Bean
    public MongoItemReader<CapturedData> capturedDataReader() {
        MongoItemReader<CapturedData> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setCollection(readFromCollectionName);
        reader.setSort(new HashMap<String, Sort.Direction>() {
            {
                put("_id", Direction.DESC);
            }
        });
        reader.setTargetType(CapturedData.class);
        reader.setQuery(new Query().addCriteria(Criteria.where("created").gte(Long.valueOf(start)).lte(Long.valueOf(end))));////where("created").gte(Long.valueOf(start)).andOperator(where("created").lte(Long.valueOf(end)))));
        return reader;
    }
//
//    @Bean
//    public MongoItemWriter<CapturedData> capturedDataWriter() {
//        MongoItemWriter<CapturedData> writer = new MongoItemWriter<>();
//        writer.setTemplate(mongoTemplate);
//        writer.setCollection(writeToCollectionName);
//        return writer;
//
//    }

}
