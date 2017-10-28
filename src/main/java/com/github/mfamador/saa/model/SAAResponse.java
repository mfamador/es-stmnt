package com.github.mfamador.saa.model;

import lombok.Data;

import java.util.List;

@Data
public class SAAResponse {

    private long count;
    private List<Document> documents;
    private List<KeyPhrase> cloud;
}
