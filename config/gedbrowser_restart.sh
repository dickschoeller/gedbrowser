#!/bin/bash
export GEDBROWSER_HOME=/var/lib/gedbrowser
export DATA_DIR=/home/dick/data

docker stop gedbrowser-api
docker stop gedbrowser
docker stop geoservice
docker stop mongo

docker rm gedbrowser-api
docker rm gedbrowser
docker rm geoservice
docker rm mongo

export R="--restart unless-stopped"
export M="--link mongo:mongo"
export H="-v ${GEDBROWSER_HOME}:/var/lib/gedbrowser"
export A="--spring.config.location=file:/var/lib/gedbrowser/application.yml"
export V="latest"
export VN=""
export HO="largo.schoellerfamily.org"
export PO="9086"

docker run -v ${DATA_DIR}:/data/db --name mongo -p 28001:27017 -d mongo
docker run ${R} ${M} ${H} -p 8086:8080 -p 8087:8081 --name geoservice${VN} -d dickschoeller/geoservice:${V}
sleep 5
docker run ${R} ${M} --link geoservice:geoservice ${H} -p 8082:8080 -p 8083:8081 --name gedbrowser${VN} -d dickschoeller/gedbrowser:${V} ${A}
sleep 5
docker run ${R} ${M} ${H} -p 8088:8080 -p 8089:8081 --name gedbrowser-api${VN} -d dickschoeller/gedbrowser-api:${V} ${A}
