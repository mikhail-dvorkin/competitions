#!/bin/bash

DIR="dgcj_tool~"

pushd .. > /dev/null
mkdir $DIR

pushd $DIR > /dev/null
wget https://code.google.com/codejam/contest/static/dcj_minimal.tar.bz
tar xf dcj_minimal.tar.bz
rm dcj_minimal.tar.bz

pushd src/parunner > /dev/null
go build
mv parunner ../../executable/
popd > /dev/null

cat sample-configs/linux-config.json | sed 's/java-7-openjdk-amd64/java-default/' | sed 's/"\/usr\/lib\/jvm\/java-default\/include"/"\/usr\/lib\/jvm\/java-default\/include","\/usr\/lib\/jvm\/java-default\/include\/linux"/' > config.json
popd > /dev/null

#./dcj_minimal/dcj.sh $*

#rm Main
