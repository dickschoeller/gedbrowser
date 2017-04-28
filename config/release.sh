#!/bin/bash

# Announce the current version. Get the next
development_version=$(grep SNAPSHOT pom.xml | sed -e 's/.*[<]version[>]//' -e 's@[<]/version[>]@@')
release_version=$(echo $development_version | sed -e 's/-SNAPSHOT//');
echo "Current development version is: $development_version"
echo "Release version will be $release_version"
read -p "Enter next release target: " next_target
new_development_version=${next_target}-SNAPSHOT

# Merge to master
git checkout master
git merge development

# Fix the pom files.
sed -i -e "s/$development_version/$release_version/" pom.xml */pom.xml

# Sanity test. Exit on failure.
mvn clean test
rc=$?; if [[ $rc != 0 ]]; then exit $rc; fi

# Report the changes and give a chance to bail.
git add -A
git status
while true; do
    read -p "Proceed?" yn
    case $yn in
        [Yy]* ) make install; break;;
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
git merge master

# Fix up the pom files.
sed -i -e "s/$release_version/$new_development_version/" pom.xml */pom.xml

git add -A
git status

echo "We are leaving you in development, with updated pom files, ready to commit."
