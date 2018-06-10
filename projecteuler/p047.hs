module Main where

import Math

find a 0 _ = a
find a n m =
  if (length (primeFactors a) == m)
    then find (a+1) (n-1) m
    else find (a+1) m m

p47 m = (find 1 m m) - toInteger m

main = print $ p47 4
