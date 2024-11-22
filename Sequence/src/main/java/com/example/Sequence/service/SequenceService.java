package com.example.Sequence.service;

import com.example.Sequence.model.Sequence;
import com.example.Sequence.repository.SequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SequenceService {

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    @Autowired
    private SequenceRepository sequenceRepository;

    public Mono<Integer> getNextSequence(String sequenceName) {
        return mongoTemplate.findAndModify(
                query(where("_id").is(sequenceName)),
                new Update().inc("seq", 1),
                options().returnNew(true).upsert(true),
                Sequence.class
        ).map(Sequence::getSeq);
    }

    // Optional method to demonstrate repository usage
    public Mono<Sequence> findById(String id) {
        return sequenceRepository.findById(id);
    }
}
