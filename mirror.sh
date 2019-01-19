#!/bin/bash

# Setup git
git config --global user.email "travis@travis-ci.org"
git config --global user.name "Travis CI"

git clone --recursive https://github.com/Beezig/BeezigLaby.git # Clone the project into the BeezigLaby directory
cd BeezigLaby # cd into the directory

# Remove the references to the submodules
rm -rf .git
rm .gitmodules

# Copy the contents of the submodules into the root folder

rsync -av Beezig/hive-api-wrapper/src/main/java/ src/main/java/
rsync -av Beezig/src/ src/main/java/ --remove-source-files
ls -a
rsync -av BeezigForge/src/main/java/ src/main/java/ --remove-source-files
rsync -av BeezigForge/src/main/resources/ src/main/resources/ --remove-source-files

rm -rf Beezig
rm -rf BeezigForge

rsync -av src/main/java/libraries/ src/main/resources/ --remove-source-files
rsync -av src/main/java/lang/ src/main/resources/ --remove-source-files
rsync -av src/main/java/core/messages/ src/main/resources/ --remove-source-files

git init # Create a blank repository
git remote add origin https://${GIT_TOKEN}@github.com/RoccoDev/BeezigLaby-Mirror.git # Add the mirror as remote
git add --all # Add all the files
git commit -m "BeezigLaby mirror commit" # Commit changes
git push origin master --force # Force push the repository

cd .. # End and change back to previous directory