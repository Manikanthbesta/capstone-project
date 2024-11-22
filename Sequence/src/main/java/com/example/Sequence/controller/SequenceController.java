package com.example.Sequence.controller;

import com.example.Sequence.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class SequenceController {

    @Autowired
    private SequenceService sequenceService;

    @GetMapping("/sequence/next")
    public Mono<Integer> getNextSequence(@RequestParam String sequenceName) {
        return sequenceService.getNextSequence(sequenceName);
    }
}
