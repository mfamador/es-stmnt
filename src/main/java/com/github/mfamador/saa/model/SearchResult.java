package com.github.mfamador.saa.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SearchResult {

    private long count;
    private List<Document> documents = new ArrayList<>();
    private List<KeyPhrase> cloud = new ArrayList<>();

    public void addDoc(Document document) {

        documents.add(document);
    }

    public void addKeyPhrase(String key, long docCount) {

        cloud.add(new KeyPhrase(key, docCount));
    }
}
