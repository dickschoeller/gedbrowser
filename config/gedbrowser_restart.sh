#!/bin/bash
export GEDBROWSER_HOME=/var/lib/gedbrowser
export DATA_DIR=/home/dick/data

docker stop gedbrowser gedbrowser-snapshot
docker stop geoservice geoservice-snapshot
docker stop mongo

docker rm gedbrowser gedbrowser-snapshot geoservice geoservice-snapshot mongo
docker rmi $(docker images | grep none | awk '{ print $3 }' )
docker rmi mongo
docker rmi dickschoeller/geoservice:latest
docker rmi dickschoeller/gedbrowser:latest
docker rmi $(docker images | grep dickschoeller | awk '{ print $3 }' )

docker run -v ${DATA_DIR}:/data/db --name mongo -p 28001:27017 -d mongo
docker run --link mongo:mongo -v ${GEDBROWSER_HOME}:/var/lib/gedbrowser -p 8086:8080 -p 8087:8081 --name geoservice -d dickschoeller/geoservice
docker run --link geoservice:geoservice --link mongo:mongo -v ${GEDBROWSER_HOME}:/var/lib/gedbrowser -p 8082:8080 -p 8083:8081 --name gedbrowser -d dickschoeller/gedbrowser
