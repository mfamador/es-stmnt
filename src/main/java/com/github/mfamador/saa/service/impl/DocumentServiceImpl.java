package com.github.mfamador.saa.service.impl;

import com.github.mfamador.saa.model.Document;
import com.github.mfamador.saa.service.DocumentService;
import lombok.extern.log4j.Log4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j
@Service
public class DocumentServiceImpl implements DocumentService {

    private TransportClient client;

    @Autowired
    public void setClient(TransportClient client) {
        log.debug("set client");
        log.debug("   client: " + client);
        this.client = client;
    }

    public List<Document> findAll() {
        log.debug("list all records");

        log.debug("client: " + client);

        if (client != null) {
            SearchResponse response = client.prepareSearch().get();
            log.debug("response: " + response);
        }

        List<Document> docList = new ArrayList<>();

        docList.add(new Document("an id", "a title", "a body", Arrays.asList("a key", "another key")));
        docList.add(new Document("another id", "another title", "another body", Arrays.asList("another key", "another key")));

        return docList;
    }
}
