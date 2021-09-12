#!/bin/bash

#../../utils/atcoder/install.sh https://img.atcoder.jp/rcl-contest-2021-long 9ee3ca1da522fff7e369dd7f470f1e7a.zip 10

URL_DIR=https://img.atcoder.jp/rcl-contest-2021-long
ARCHIVE=9ee3ca1da522fff7e369dd7f470f1e7a.zip
NUM_TESTS=100

wget $URL_DIR/$ARCHIVE
rm tools~.zip
rm -rf tools~
mv $ARCHIVE tools~.zip
unzip tools~.zip
mv A tools~
