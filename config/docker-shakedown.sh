#!/bin/bash
docker run --link mongo:mongo -v /home/travis/gedbrowser:/var/lib/gedbrowser -p 8086:8080 -p 8087:8080 --name geoservice -d dickschoeller/geoservice:snapshot
docker run --link mongo:mongo --link geoservice:geoservice -v /home/travis/gedbrowser:/var/lib/gedbrowser -p 8080:8080 -p 8081:8081 --name gedbrowser -d dickschoeller/gedbrowser:snapshot
echo "Wait a minute"
sleep 1m
echo "Done waiting, let's see how Docker has done"
docker images
docker ps --all
docker logs geoservice
