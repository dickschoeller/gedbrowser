#!/bin/bash
mkdir /home/travis/data
mkdir /home/travis/gedbrowser
cp $(find . -name 'gl120368.ged' | head -n 1) /home/travis/gedbrowser
cp $(find . -name 'mini-schoeller.ged' | head -n 1) /home/travis/gedbrowser
cp $(find . -name 'testUserFile.csv' | head -n 1) /home/travis/gedbrowser
cp $(find . -name 'test.txt' | head -n 1) /home/travis/gedbrowser
echo $GOOGLE_GEOCODING_KEY > /home/travis/gedbrowser/google-geocoding-key
echo $GOOGLE_MAPPING_KEY >> /home/travis/gedbrowser/google-geocoding-key
docker run -v /home/travis/data:/data/db --name mongo -p 28001:27017 -d mongo 2>&1 | grep 'Status'
docker pull java:8 2>&1 | grep 'Status'
