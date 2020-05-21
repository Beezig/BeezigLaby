#!/bin/bash

# Setup git
git config --global user.email "travis@travis-ci.org"
git config --global user.name "Travis CI"

resourceDirs=(lang core META-INF assets)
./gradlew build -DFORGESRG=true
cd build/libs
mkdir extract
mkdir -p newSrc/src/main/java
mkdir -p newSrc/src/main/resources
cp BeezigLaby-*.jar extract

# Extract jar for resources
cd extract
unzip BeezigLaby-*.jar
rm BeezigLaby-*.jar
for dir in "${resourceDirs[@]}"
do
    mv $dir ../newSrc/src/main/resources
done
rm -rf $(find * -prune -type d)
mv * ../newSrc/src/main/resources
cd ..

wget https://rocco.dev/beezighosting/cfr.jar
wget https://rocco.dev/beezighosting/bon.jar
rm BeezigLaby-*-sources.jar
java -jar bon.jar --inputJar BeezigLaby-*.jar --mappingsVer 22 --outputJar BeezigLaby-deobf.jar
java -jar cfr.jar BeezigLaby-deobf.jar --outputdir newSrc/src/main/java
cd newSrc/src/main/java
find . -name "package-info.java" -delete
rm -r ../resources/META-INF
cd ../../../
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
./gradlew clean