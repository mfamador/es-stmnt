#!/bin/bash

if [ "$(docker ps -q -f name=hct-es)" ]; then
    docker stop hct-es
    if [ "$(docker ps -aq -f status=exited -f name=hct-es)" ]; then
        docker rm hct-es
    fi
fi
