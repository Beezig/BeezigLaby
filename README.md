[![Discord](https://discordapp.com/api/guilds/346695724253184014/embed.png?style=banner2)](http://discord.gg/se7zJsU)  
![Github All Releases](https://img.shields.io/github/downloads/Beezig/BeezigLaby/total.svg)
![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/Beezig/BeezigLaby.svg)
![Unique users](https://l.beezig.eu/badgeunique)
![LOC](https://badgen.net/codeclimate/loc/Beezig/BeezigLaby)

# BeezigLaby
LabyMod expansion classes for [Beezig](https://github.com/Beezig/Beezig), the Hive plugin for 5zig.

## Download
The recommended way to install BeezigLaby is via the in-game plugin browser or the LabyMod installer.

For raw files, check the [releases](https://github.com/Beezig/BeezigLaby/releases) tab.

## Build from source
First, clone the project:
```
git clone --recursive https://github.com/Beezig/BeezigLaby.git
```
(The `--recursive` flag is needed cause it has to clone different submodules)

Then, build using Gradle:
```
gradle setupDecompWorkspace build
```
