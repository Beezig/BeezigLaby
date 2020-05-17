#!/bin/bash

# Setup git
git config --global user.email "travis@travis-ci.org"
git config --global user.name "Travis CI"

resourceDirs=(lang core META-INF assets)

cd build/libs
mkdir newSrc
wget https://rocco.dev/beezighosting/fernflower.jar
java -jar fernflower.jar BeezigLaby-*.jar newSrc/
cd newSrc
rm BeezigLaby-*-sources.jar
unzip BeezigLaby-*.jar
rm BeezigLaby-*.jar
mkdir -p src/main/java
mv $(find * -prune -type d) src/main/java
mkdir -p src/main/resources
mv * src/main/resources
cd src/main/java
for dir in "${resourceDirs[@]}"
do
    mv $dir ../resources
done
rm -r ../resources/META-INF
cd -
cp ../../../EMBED .
cp ../../../PROVIDE .
cp -r ../../../libs .
mv ../../../MIRROR.md ./README.md
git init # Create a blank repository
git remote add origin https://${GIT_TOKEN}@github.com/RoccoDev/BeezigLaby-Mirror.git # Add the mirror as remote
git add --all # Add all the files
git commit -m "BeezigLaby mirror commit" # Commit changes
git push origin master --force # Force push the repository
cd ../../../