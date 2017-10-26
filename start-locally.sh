#!/bin/bash

if [ ! "$(docker ps -q -f name=hct-es)" ]; then
    if [ "$(docker ps -aq -f status=exited -f name=hct-es)" ]; then
        docker start hct-es
    else
	docker run -d --name=hct-es -p 9200:9200 -p 9300:9300 ES-IMG # replace ES-IMG with populated es image
    fi
fi

mvn spring-boot:run
