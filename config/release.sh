#!/bin/bash
git checkout master
git merge development
development_version=$(grep SNAPSHOT pom.xml | sed -e 's/.*[<]version[>]//' -e 's@[<]/version[>]@@')
release_version=$(echo $development_version | sed -e 's/-SNAPSHOT//');
echo "Current development version is: $development_version"
echo "Release version will be $release_version"
read -p "Enter next release target: " next_target
new_development_version=${next_target}-SNAPSHOT
sed -i -e "s/$development_version/$release_version/" pom.xml */pom.xml
mvn clean install
rc=$?; if [[ $rc != 0 ]]; then exit $rc; fi
git add pom.xml */pom.xml
git commit
git push
git checkout development
git merge master
sed -i -e "s/$release_version/$new_development_version/" pom.xml */pom.xml
