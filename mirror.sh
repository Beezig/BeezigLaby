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
rm Beezig/src/pw

rsync -av Beezig/src/ src/main/java/
ls -a
rsync -av submodules/src/ src/main/java/
rsync BeezigForge/src/main/java/ src/main/java/

rm -rf Beezig
rm -rf BeezigForge

rsync -av src/main/java/libraries/ src/main/resources/
rsync -av src/main/java/lang/ src/main/resources/
rsync -av src/main/java/core/messages/ src/main/resources/

git init # Create a blank repository
git remote add origin https://${GIT_TOKEN}@github.com/RoccoDev/BeezigLaby-Mirror.git # Add the mirror as remote
git add --all &> /dev/null # Add all the files
git commit -m "BeezigLaby mirror commit" &> /dev/null # Commit changes
git push origin master --force &> /dev/null # Force push the repository

cd .. # End and change back to previous directory