#!/bin/bash

URL_DIR=$1
ARCHIVE=$2
ARCHIVE_WINDOWS=${ARCHIVE/.zip/_windows.zip}
NUM_TESTS=$3

if [ ! -f "tools~.zip" ]; then
	curl $URL_DIR/$ARCHIVE -o tools~.zip
fi
if [ ! -f "tools_windows~.zip" ]; then
	curl $URL_DIR/$ARCHIVE_WINDOWS -o tools_windows~.zip
fi
rm -rf tools~
unzip tools~.zip
mv tools tools~
cd tools~
if [ -n "$NUM_TESTS" ]; then
	rm seeds.txt
	for ((i=0; i < $NUM_TESTS; i++))
	do
	    echo $i>> seeds.txt
	done
fi
cargo update
cargo build
cargo run --release --bin gen < seeds.txt
cargo run --release --bin gen seeds.txt
cargo run --release --bin vis example.in example.out

mkdir windows
cd windows
unzip ../../tools_windows~.zip
