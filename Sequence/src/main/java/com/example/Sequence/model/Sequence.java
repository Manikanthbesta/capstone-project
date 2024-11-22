package com.example.Sequence.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sequences")
@Getter
@Setter
public class Sequence {
    @Id
    private String id;
    private Integer seq;
}
