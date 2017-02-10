#!/bin/bash
if [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
    openssl aes-256-cbc -K $encrypted_0b79b78d9b0a_key -iv $encrypted_0b79b78d9b0a_iv -in config/codesigning.asc.enc -out config/codesigning.asc -d
    gpg --fast-import config/codesigning.asc
else
    echo "Pull request, no code signing"
fi
