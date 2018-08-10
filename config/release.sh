#!/bin/bash

# Announce the current version. Get the next
development_version=$(grep '[<]version[>].*SNAPSHOT' pom.xml | head -n 1 | sed -e 's/.*[<]version[>]//' -e 's@[<]/version[>]@@')
release_version=$(echo $development_version | sed -e 's/-SNAPSHOT//');
echo "Current development version is: $development_version"
echo "Release version will be $release_version"
read -p "Enter next release target: " next_target
new_development_version=${next_target}-SNAPSHOT
echo "New development version will be $new_development_version"

# Merge to master
git checkout master
git pull
git merge --strategy-option=theirs development

# Fix the version numbers in files.
sed -i -e "s/$development_version/$release_version/" pom.xml */pom.xml
sed -i -e "s/$development_version/$release_version/" gedbrowser/src/test/java/org/schoellerfamily/gedbrowser/test/ApplicationInfoTest.java
sed -i -e "s/$development_version/$release_version/" gedbrowser/src/main/docker/Dockerfile
sed -i -e "s/$development_version/$release_version/" gedbrowser/src/main/resources/banner.txt
sed -i -e "s/$development_version/$release_version/" gedbrowser-datamodel/src/main/java/org/schoellerfamily/gedbrowser/datamodel/GedObject.java
sed -i -e "s/$development_version/$release_version/" geoservice/src/test/java/org/schoellerfamily/geoservice/test/ApplicationInfoTest.java
sed -i -e "s/$development_version/$release_version/" geoservice/src/main/docker/Dockerfile
sed -i -e "s/$development_version/$release_version/" geoservice/src/main/java/org/schoellerfamily/geoservice/controller/ApplicationInfo.java
sed -i -e "s/$development_version/$release_version/" geoservice/src/main/resources/banner.txt
sed -i -e "s/$development_version/$release_version/" README.md
sed -i -e "s/$development_version/$release_version/" gedbrowserng/src/main/resources/banner.txt
sed -i -e "s/$development_version/$release_version/" gedbrowserng/src/main/docker/Dockerfile
sed -i -e "s/XXXXXX/$release_version/" config/deploy.sh
sed -i -e "s/XXXXXX/$release_version/" config/before-deploy.sh

# Fix published docker version
sed -i -e "s/docker.image.tag.snapshot/docker.image.tag>$release_version/" pom.xml
sed -i -e 's/imageTag>snapshot/imageTag>latest/' gedbrowser/pom.xml geoservice/pom.xml gedbrowserng/pom.xml

# Sanity test. Exit on failure.
mvn clean test
rc=$?; if [[ $rc != 0 ]]; then exit $rc; fi

# Report the changes and give a chance to bail.
git add -A
git status
while true; do
    read -p "Proceed? " yn
    case $yn in
        [Yy]* ) mvn install; break;;
        [Nn]* ) exit;;
        * ) echo "Please answer yes or no.";;
    esac
done

# Last chance to stop the commit by not filling in the comments.
git commit
rc=$?; if [[ $rc != 0 ]]; then exit $rc; fi

# Send it downstream
git push

# Merge back to development
git checkout development
##### this next step doesn't work ####
git merge --strategy-option=theirs master

# Fix up the pom files.
sed -i -e "s/$release_version/$new_development_version/" pom.xml */pom.xml
sed -i -e "s/$release_version/$new_development_version/" gedbrowser/src/test/java/org/schoellerfamily/gedbrowser/test/ApplicationInfoTest.java
sed -i -e "s/$release_version/$new_development_version/" gedbrowser/src/main/docker/Dockerfile
sed -i -e "s/$release_version/$new_development_version/" gedbrowser/src/main/resources/banner.txt
sed -i -e "s/$release_version/$new_development_version/" gedbrowser-datamodel/src/main/java/org/schoellerfamily/gedbrowser/datamodel/GedObject.java
sed -i -e "s/$release_version/$new_development_version/" geoservice/src/test/java/org/schoellerfamily/geoservice/test/ApplicationInfoTest.java
sed -i -e "s/$release_version/$new_development_version/" geoservice/src/main/docker/Dockerfile
sed -i -e "s/$release_version/$new_development_version/" geoservice/src/main/java/org/schoellerfamily/geoservice/controller/ApplicationInfo.java
sed -i -e "s/$release_version/$new_development_version/" geoservice/src/main/resources/banner.txt
sed -i -e "s/$release_version/$new_development_version/" README.md
sed -i -e "s/$release_version/$new_development_version/" gedbrowserng/src/main/resources/banner.txt
sed -i -e "s/$release_version/$new_development_version/" gedbrowserng/src/main/docker/Dockerfile

sed -i -e "s/docker.image.tag.$release_version/docker.image.tag>snapshot/" pom.xml
sed -i -e 's/imageTag>latest/imageTag>snapshot/' gedbrowser/pom.xml geoservice/pom.xml gedbrowserng/pom.xml
sed -i -e "s/$release_version/XXXXXX/" config/deploy.sh
sed -i -e "s/$release_version/XXXXXX/" config/before-deploy.sh

git add -A
git status

echo "We are leaving you in development, with updated pom files, ready to commit."
