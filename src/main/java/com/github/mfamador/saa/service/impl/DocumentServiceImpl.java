package com.github.mfamador.saa.service.impl;

import com.github.mfamador.saa.model.Document;
import com.github.mfamador.saa.service.DocumentService;
import lombok.extern.log4j.Log4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

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

            /* Sample query
                curl -XPOST 'http://localhost:9200/documents/_search?pretty' -d '{
                    "from": 0, "size": 100,
                    "query": {
                        "bool" : {
                            "must": [
                               {"query_string": {
                                   "query": "volvo AND 60",
                                   "fields": ["title", "body"]}}],
                            "filter": {
                                  "bool": {
                                      "should": [
                                          {"term": { "sentiment": "p"}},
                                          {"term": { "sentiment": "n"}}]}}}},
                    "aggs": {"cloud": {"terms": { "field" : "keyPhrases"}}}
                }'
             */

            SearchResponse response = client
              .prepareSearch("documents")
              .setTypes("document")
              .setFrom(0)
              .setSize(100)
              .setQuery(boolQuery()
                .must(
                  queryStringQuery("volvo AND 60").field("title").field("body"))
                .filter(
                  boolQuery()
                    .should(termQuery("sentiment", "v"))
                    .should(termQuery("sentiment", "n"))
                  ))
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

        docList.forEach(d -> {
            log.debug("doc : " + d.getTitle() + " (" + d.getSentiment() + ")");
        });

        return docList;
    }
}
