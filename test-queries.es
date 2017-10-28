
## Test query

## if we want (is asked) to use query string
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

## if we want phrase match
curl -XPOST 'http://localhost:9200/documents/_search?pretty' -d '{
    "from": 0, "size": 100,
    "query": {
        "bool" : {
            "must": [
               {"multi_match": { 
                     "query": "volvo 60",
                     "type": "phrase",
	             "fields": ["title", "body"]}}],
            "filter": {
                  "bool": {
                      "should": [
                          {"term": { "sentiment": "p"}},
                          {"term": { "sentiment": "n"}}]}}}},
    "aggs": {"cloud": {"terms": { "field" : "keyPhrases"}}}
}'
