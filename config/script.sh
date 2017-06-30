#!/bin/bash
mvn install jacoco:report coveralls:report --settings=config/settings.xml -Ptravis-build -DrepoToken=${REPOTOKEN} -B -Dspring.data.mongodb.port=28001 -Dgedbrowser.home=/home/travis/gedbrowser -Dgeoservice.keyfile=stub -Dgeoservice.backupfile=/home/travis/gedbrowser/geoservice-test-backup.json -DpushImage -DpushImageTag
