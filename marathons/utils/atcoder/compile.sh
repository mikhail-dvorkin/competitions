#!/bin/bash

rm solution~.jar solution_submit~.kt solution_submit~.kt.txt
cat $1 | sed -E 's/^(.*\/\/TESTING\s*)$/\/\/\1/' | sed -E 's/^\/\/(.*\/\/SUBMISSION\s*)$/\1/' > solution_submit~.kt
kotlinc -include-runtime -d solution~.jar solution_submit~.kt
mv solution_submit~.kt solution_submit~.kt.txt
