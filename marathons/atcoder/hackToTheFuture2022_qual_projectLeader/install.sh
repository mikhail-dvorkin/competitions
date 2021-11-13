#!/bin/bash

URL_DIR=https://img.atcoder.jp/future-contest-2022-qual
ARCHIVE=f4ca7c3336de23e5c8d1338981e38375.zip
NUM_TESTS=10

wget $URL_DIR/$ARCHIVE
rm tools~.zip
rm -rf tools~
mv $ARCHIVE tools~.zip
unzip tools~.zip
mv tools tools~
