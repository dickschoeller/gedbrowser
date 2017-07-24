#!/bin/bash
browser=$1
platform=$2
version=$3
echo TRAVIS_BUILD_NUMBER=${TRAVIS_BUILD_NUMBER}
echo TRAVIS_JOB_NUMBER=${TRAVIS_JOB_NUMBER}
echo browser=${browser}
echo platform=${platform}
echo version=${version}
if [ -z $version ]; then
    varg=""
else
    varg="-Dselenium.browser.version=${version}"
fi
echo mvn integration-test verify ${INTEGRATION_TEST_ARGS} -Dselenium.browser.name=${browser} -Dselenium.platform=${platform} ${varg} -Dselenium.timeout=120
mvn integration-test verify ${INTEGRATION_TEST_ARGS} -Dselenium.browser.name=${browser} -Dselenium.platform=${platform} ${varg} -Dselenium.timeout=120
