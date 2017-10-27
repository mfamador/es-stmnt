


curl -XPOST 'http://localhost:9200/documents/_search?pretty' -d '{
    "query" : {
        "match_all" : {}
    },
    "aggs" : {
        "cloud" : {
            "terms" : { "field" : "keyPhrases"}
        }
    }
}'

