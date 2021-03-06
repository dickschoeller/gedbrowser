#!/bin/bash
if [[ $TRAVIS_PULL_REQUEST == 'false' && ( $TRAVIS_BRANCH == 'XXXXXX' || $TRAVIS_BRANCH == 'development' ) ]]; then
    mvn --batch-mode --settings=config/settings.xml -Psign,deploy -DpushImage deploy
else
    echo "Only deploy on push to development or label"
fi
