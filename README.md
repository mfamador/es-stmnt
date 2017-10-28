# Sentiment analysis API

Spring Boot and Elasticsearch API


### Getting started

Start the ES container

```
docker run -d --name=hct-es -p 9200:9200 -p 9300:9300 ES-IMG:latest # replace with correct image name
```

Example doc

```json
{
    "title": "a title",
    "body": "a body",
    "keyPhrases": [
        "xpto1",
        "xpto2",
        "xpto3"],
    "sentiment": "n"
}
```

Running locally

```
mvn spring-boot:run
```

Invoking the API

```
curl -XPOST http://localhost:8080/saa -H 'Content-Type: application/json' -d '{
    "from": 0, "size": 100,
    "query": "a AND b AND c",
    "sentiment": "n,v,p",
    "cloud": 10
}'
```

```
curl -XPOST http://localhost:8080/saa -H 'Content-Type: application/json' -d '{
    "query": "a AND b AND c"
}'
```

Request default values:
```
- from: 0
- size: 25
- cloud: 10
```
