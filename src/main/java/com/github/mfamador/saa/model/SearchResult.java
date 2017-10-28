package com.github.mfamador.saa.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SearchResult {

    private long count;
    private List<Document> documents;
    private List<KeyPhrase> cloud;

    public void addDoc(Document document) {
        if (documents == null) documents = new ArrayList<>();

        documents.add(document);
    }

    public void addKeyPhrase(String key, long docCount) {
        if (cloud == null) cloud = new ArrayList<>();

        cloud.add(new KeyPhrase(key, docCount));
    }
}
