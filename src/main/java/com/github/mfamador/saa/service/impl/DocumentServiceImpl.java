package com.github.mfamador.saa.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mfamador.saa.model.Document;
import com.github.mfamador.saa.model.SearchRequest;
import com.github.mfamador.saa.model.SearchResult;
import com.github.mfamador.saa.service.DocumentService;
import lombok.extern.log4j.Log4j;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

@Log4j
@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private TransportClient client;

    public SearchResult find(SearchRequest request) {

        SearchResult result = new SearchResult();

        if (client != null) {
            BoolQueryBuilder query = boolQuery()
              .must(request.getQuery() != null && !request.getQuery().isEmpty() ?
                queryStringQuery(request.getQuery()).field("title").field("body") :
                matchAllQuery());

            if (request.getSentiment() != null && !request.getSentiment().isEmpty()) {
                String[] sentiments = request.getSentiment().split(",");
                BoolQueryBuilder filter = boolQuery();
                for (String sentiment : sentiments)
                    filter.should(termQuery("sentiment", sentiment));
                query.filter(filter);
            }

            SearchRequestBuilder searchRequest = client.prepareSearch("documents")
              .setTypes("document")
              .setFrom(request.getFrom())
              .setSize(request.getSize() == null || request.getSize() > 100 ? 100 : request.getSize())
              .setQuery(query);

            if (request.getCloud() > 0)
                searchRequest.addAggregation(AggregationBuilders
                  .terms("cloud")
                  .field("keyPhrases")
                  .order(Terms.Order.count(false)) // unnecessary
                  .size(request.getCloud()));

            SearchResponse response = searchRequest.get();

            if (request.getCloud() > 0)
                response.getAggregations().asMap().forEach((key, value) -> {
                    if ("cloud".equals(key))
                        ((StringTerms) value).getBuckets().forEach(b -> result
                          .addKeyPhrase((String) b.getKey(), b.getDocCount()));
                });

            response.getHits().forEach(hit -> {
                Document doc = new Document();
                doc.setId(hit.getId());
                hit.getSourceAsMap().forEach((key, value) -> {
                    if ("title".equals(key))
                        doc.setTitle((String) value);
                    else if ("body".equals(key))
                        doc.setBody((String) value);
                    else if ("sentiment".equals(key))
                        doc.setSentiment((String) value);
                    else if ("keyPhrases".equals(key))
                        doc.setKeyPhrases((List<String>) value);
                });
                result.addDoc(doc);
            });
            result.setCount(response.getHits().getTotalHits());
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            log.debug(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
        } catch (JsonProcessingException ex) {
            log.error(ex.getMessage());
        }

        return result;
    }
}
