#!/bin/bash
export GEDBROWSER_HOME=/var/lib/gedbrowser
export DATA_DIR=/home/dick/data

docker stop gedbrowserng-snapshot
docker stop gedbrowser-snapshot
docker stop geoservice-snapshot

docker rm gedbrowserng-snapshot
docker rm gedbrowser-snapshot
docker rm geoservice-snapshot

export R="--restart unless-stopped"
export M="--link mongo:mongo"
export H="-v ${GEDBROWSER_HOME}:/var/lib/gedbrowser"
export A="--spring.config.location=file:/var/lib/gedbrowser/application-snapshot.yml"
export V="snapshot"
export VN="-snapshot"
export HO="largo.schoellerfamily.org"
export PO="9086"

docker ps | grep mongo
if [ $? = 1 ]; then
    docker run -v ${DATA_DIR}:/data/db --name mongo -p 28001:27017 -d mongo
fi
docker run ${R} ${M} ${H} -p 9086:8080 -p 9087:8081 --name geoservice${VN} -d dickschoeller/geoservice:${V} ${A}
sleep 5
docker run ${R} -e "geoservice.host=${HO}" -e "geoservice.port=${PO}" ${M} --link geoservice:geoservice ${H} -p 9082:8080 -p 9083:8081 --name gedbrowser${VN} -d dickschoeller/gedbrowser:${V} ${A}
sleep 5
docker run ${R} ${M} ${H} -p 9088:8080 -p 9089:8081 --name gedbrowserng${VN} -d dickschoeller/gedbrowserng:${V} ${A}
