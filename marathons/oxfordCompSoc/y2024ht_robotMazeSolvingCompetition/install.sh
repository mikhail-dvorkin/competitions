#!/bin/bash

if [ ! -f "tools~.zip" ]; then
	curl https://codeforces.com/group/WfpD43Y7Ig/contest/497688/problem-materials/problem-a-additional-materials.zip -o tools~.zip
fi
rm -rf tools~
unzip tools~.zip
mv files/* .
rm -rf files
unzip additional_materials.zip
rm additional_materials.zip
mv public tools~
cd tools~
mkdir in
cp samples/statement_samples/* in
cp samples/extra_samples/* in
