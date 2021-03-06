#!/bin/bash
set -e

# Setup git
git config --global user.email "gitlab-bot@beezig.eu"
git config --global user.name "Beezig Bot"

cd build/libs
mkdir -p unzipped/src/main/resources
mkdir -p unzipped/src/main/java
cp BeezigLaby-*.jar unzipped/src/main/resources
cd unzipped/src/main/resources
echo "public class Dummy {}" > ../java/Dummy.java
unzip BeezigLaby-*.jar
rm BeezigLaby-*.jar
cd ../../../
cp -r ../../../libs .
touch PROVIDE
touch EMBED
mv ../../../MIRROR.md ./README.md
git init # Create a blank repository
git remote add origin https://beezig-bot:${MIRROR_KEY}@gitlab.com/Beezig/BeezigLaby-Mirror.git # Add the mirror as remote
git add --all # Add all the files
git commit -m "BeezigLaby mirror commit" # Commit changes
git push origin master --force # Force push the repository
cd ../../../