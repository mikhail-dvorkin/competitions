module Main where

import Math
import List

good n = isPrime n && head (sort $ digits n) > 0 && check (digitShift n) n

check a n =
  if a == n
    then True
    else isPrime a && check (digitShift a) n

main = print $ length $ filter good [1..999999]
