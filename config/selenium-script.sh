#!/bin/bash
browser=$1
platform=$2
echo TRAVIS_BUILD_NUMBER=${TRAVIS_BUILD_NUMBER}
echo TRAVIS_JOB_NUMBER=${TRAVIS_JOB_NUMBER}
echo browser=${browser}
echo platform=${platform}
mvn integration-test verify ${INTEGRATION_TEST_ARGS} -Dselenium.browser.name=${browser} -Dselenium.platform=${platform} -Dselenium.timeout=120
