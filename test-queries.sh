#!/bin/bash

## test query
curl -XPOST 'http://localhost:9200/documents/_search?pretty' -d '{
    "from": 0,
    "size": 100,
    "query": {
        "bool": {
            "must": [{
                "query_string": {
                    "query": "volvo AND v60",
  	                "fields": ["title", "body"]}}],
            "filter": {
                "bool": {
                    "should": [{
                        "term": { "sentiment": "v"}},{
                        "term": { "sentiment": "n"}}]}}}},
    "aggs": {
        "cloud": {
            "terms": {
                "size": 3, 
                "field": "keyPhrases"}}}
}'

## same with REST API
curl -XPOST http://localhost:8080/saa -H 'Content-Type: application/json' -d '{
     "from": 0,
     "size": 100,
     "query": "volvo AND v60",
     "sentiment": "v,n",
     "cloud":3
}'
