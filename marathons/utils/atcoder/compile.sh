#!/bin/bash

rm solution~.jar solution_submit~.kt solution_submit~.kt.txt
echo "//`date -u`" > solution_submit~.kt
cat $1 \
| sed -E 's/^(.*\/\/\s*TESTING\s*)$/\/\/\1/' \
| sed -E 's/^\/\/(.*\/\/\s*SUBMISSION\s*)$/\1/' \
| sed -E 's/^(package\s.*)$/\/\/\1/' \
| sed -E 's/^(\s*require\(.*)$/\/\/\1/' \
| sed -E 's/^(\s*log\s*\{.*\}\s*)$/\/\/\1/' \
| sed -E 's/^(\s*info\s*\{.*\}\s*)$/\/\/\1/' \
>> solution_submit~.kt
kotlinc -include-runtime -d solution~.jar solution_submit~.kt
mv solution_submit~.kt solution_submit~.kt.txt
