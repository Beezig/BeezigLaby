#!/bin/bash

# Setup git
git config --global user.email "travis@travis-ci.org"
git config --global user.name "Travis CI"

git clone --recursive https://github.com/Beezig/BeezigLaby.git # Clone the project into the BeezigLaby directory
cd BeezigLaby # cd into the directory

# Remove the references to the submodules
rm -rf .git
rm .gitmodules

git init # Create a blank repository
git remote add origin https://${GIT_TOKEN}@github.com/RoccoDev/BeezigLaby-Mirror.git # Add the mirror as remote
git add --all # Add all the files
git commit -m "BeezigLaby mirror commit" # Commit changes
git push origin master --force # Force push the repository
