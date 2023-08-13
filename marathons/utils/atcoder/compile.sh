#!/bin/bash

rm solution~.jar solution_submit~.kt
cat $1 | sed -E 's/^(.*\/\/TESTING\s*)$/\/\/\1/' | sed -E 's/^\/\/(.*\/\/SUBMISSION\s*)$/\1/' > solution_submit~.kt
kotlinc -include-runtime -d solution~.jar solution_submit~.kt
