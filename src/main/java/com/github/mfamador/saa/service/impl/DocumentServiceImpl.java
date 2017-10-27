package com.github.mfamador.saa.service.impl;

import com.github.mfamador.saa.model.Document;
import com.github.mfamador.saa.service.DocumentService;
import lombok.extern.log4j.Log4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j
@Service
public class DocumentServiceImpl implements DocumentService {

    private TransportClient client;

    @Autowired
    public void setClient(TransportClient client) {
        this.client = client;
    }

    public List<Document> findAll() {
        log.debug("list all records");

        List<Document> docList = new ArrayList<>();

        if (client != null) {
            SearchResponse response = client
              .prepareSearch("documents")
              .setTypes("document")
              .setFrom(0)
              .setSize(1000)
              .addAggregation(
                AggregationBuilders.terms("cloud").field("keyPhrases")
              )
              .get();

            log.debug("total hits: " + response.getHits().getTotalHits());

            response.getAggregations()
              .asMap()
              .entrySet().stream()
              .forEach(e -> {
                  if("cloud".equals(e.getKey())) {

                      ((StringTerms)e.getValue()).getBuckets().forEach(b -> {
                          log.debug(b.getKey() + " - " + b.getDocCount());
                      });
                  }
              });

            for (SearchHit hit : response.getHits()) {
                Document doc = new Document();
                doc.setId(hit.getId());
                hit.getSourceAsMap().entrySet().stream().forEach(e -> {
                    if ("title".equals(e.getKey())) {
                        doc.setTitle((String) e.getValue());
                    } else if ("body".equals(e.getKey())) {
                        doc.setBody((String) e.getValue());
                    } else if ("sentiment".equals(e.getKey())) {
                        doc.setSentiment((String) e.getValue());
                    } else if ("keyPhrases".equals(e.getKey())) {
                        doc.setKeyPhrases((List<String>) e.getValue());
                    }
                });

                docList.add(doc);
            }
        }

        return docList;
    }
}
