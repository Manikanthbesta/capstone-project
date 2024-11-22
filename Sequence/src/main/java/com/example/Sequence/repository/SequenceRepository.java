package com.example.Sequence.repository;

import com.example.Sequence.model.Sequence;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceRepository extends ReactiveMongoRepository<Sequence, String> {
    // Custom query methods (if any) can be added here
}
