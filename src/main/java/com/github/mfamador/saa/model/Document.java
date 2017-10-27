package com.github.mfamador.saa.model;

import lombok.Data;

import java.util.List;

@Data
public class Document {

    private String id;

    private String title;

    private String body;

    private String sentiment;

    private List<String> keyPhrases;
}
