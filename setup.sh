#!/bin/sh
BIN_FOLDER="$HOME/bin"
BIN_NAME="$BIN_FOLDER/files-generator"
URL=https://github.com/nishirken/files-generator/raw/master/build/bin/macos/releaseExecutable/files-generator.kexe

wget $URL -P $BIN_NAME -O $BIN_NAME
chmod +x $BIN_NAME
