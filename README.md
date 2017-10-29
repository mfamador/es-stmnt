# Sentiment analysis API

#### Getting started

Run integration tests

```
mvn verify
```


Start the ES container

```
mvn docker:start
```


Stop the ES container

```
mvn docker:stop
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
    "query": "a AND b AND c",`
    "sentiment": "n,v,p",
    "cloud": 10
}'
```

```
curl -XPOST http://localhost:8080/saa -H 'Content-Type: application/json' -d '{
    "query": "a AND b AND c"
}'
```

Search only in one field

```
curl -XPOST http://localhost:8080/saa -H 'Content-Type: application/json' -d '{
    "query": "title: a"
}'
```

Count documents

```
curl -XPOST http://localhost:8080/saa -H 'Content-Type: application/json' -d '{
    "size": 0, "cloud": 0
}'
```

Default values:

```
- from: 0
- size: 25
- cloud: 10
```

