#!/bin/bash

## test query
curl -XPOST 'http://localhost:9200/documents/_search?pretty' -H 'Content-Type: application/json' -d '{
    "from": 0,
    "size": 100,
    "query": {
        "bool": {
            "must": [{
                "query_string": {
                    "query": "a OR b",
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
     "query": "a OR b",
     "sentiment": "v,n",
     "cloud":3
}'
