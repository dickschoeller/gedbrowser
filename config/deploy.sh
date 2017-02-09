#!/bin/bash
if [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
    mvn --batch-mode --settings=config/settings.xml -Psign,deploy -DpushImage deploy
fi
