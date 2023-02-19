#!/bin/bash

URL_DIR=$1
ARCHIVE=$2
NUM_TESTS=$3

curl $URL_DIR/$ARCHIVE -o $ARCHIVE
rm tools~.zip
rm -rf tools~
mv $ARCHIVE tools~.zip
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
curl $URL_DIR/${ARCHIVE/.zip/_windows.zip} -o tools_win.zip
unzip tools_win.zip
