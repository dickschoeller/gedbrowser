#!/bin/bash
echo TRAVIS_BRANCH=$TRAVIS_BRANCH
echo TRAVIS_PULL_REQUEST=$TRAVIS_PULL_REQUEST
if [[ $TRAVIS_PULL_REQUEST == 'false' && ( $TRAVIS_BRANCH == '1.2.0' || $TRAVIS_BRANCH == 'development' ) ]]; then
    openssl aes-256-cbc -K $encrypted_0b79b78d9b0a_key -iv $encrypted_0b79b78d9b0a_iv -in config/codesigning.asc.enc -out config/codesigning.asc -d
    gpg --fast-import config/codesigning.asc
else
    echo "Only sign on push to development or label"
fi
