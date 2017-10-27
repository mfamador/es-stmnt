package com.github.mfamador.saa.model;

import lombok.Value;

import java.util.List;

@Value
public class Document {

    private String id;

    private String title;

    private String body;

    private List<String> keyPhrases;
}
