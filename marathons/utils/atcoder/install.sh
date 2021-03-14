#!/bin/bash

URL_DIR=$1
ARCHIVE=$2
NUM_TESTS=$3

wget $URL_DIR/$ARCHIVE
rm tools~.zip
mv $ARCHIVE tools~.zip
unzip tools~.zip
mv tools tools~
cd tools~
rm seeds.txt
for ((i=0; i < $NUM_TESTS; i++))
do
    echo $i>> seeds.txt
done
cargo run --release --bin gen < seeds.txt
cargo run --release --bin vis example.in example.out
