#!/bin/bash

# Setup git
git config --global user.email "travis@travis-ci.org"
git config --global user.name "Travis CI"

cd build/libs
mkdir -p unzipped/src/main/resources
cp BeezigLaby-*.jar unzipped/src/main/resources
cd unzipped/src/main/resources
unzip BeezigLaby-*.jar
rm BeezigLaby-*.jar
cd ../../../
cp -r ../../../libs .
mv ../../../MIRROR.md ./README.md
git init # Create a blank repository
git remote add origin https://${GIT_TOKEN}@github.com/RoccoDev/BeezigLaby-Mirror.git # Add the mirror as remote
git add --all # Add all the files
git commit -m "BeezigLaby mirror commit" # Commit changes
git push origin master --force # Force push the repository
cd ../../../