#!/bin/bash

# Setup git
git config --global user.email "travis@travis-ci.org"
git config --global user.name "Travis CI"

resourceDirs=(lang core META-INF assets)
./gradlew build -DFORGESRG=true
cd build/libs
wget https://rocco.dev/beezighosting/jd-cli.jar
wget https://rocco.dev/beezighosting/bon.jar
rm BeezigLaby-*-sources.jar
java -jar bon.jar --inputJar BeezigLaby-*.jar --mappingsVer 22 --outputJar BeezigLaby-deobf.jar
java -jar jd-cli.jar BeezigLaby-deobf.jar -od newSrc
cd newSrc
mkdir -p src/main/java
mv $(find * -prune -type d) src/main/java
mkdir -p src/main/resources
mv * src/main/resources
cd src/main/java
for dir in "${resourceDirs[@]}"
do
    mv $dir ../resources
done
find . -name "package-info.java" -delete
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
./gradlew clean