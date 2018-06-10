module Main where

import Math

check n b =
  if (n < 2)
    then False
    else if isPrime n
      then True
      else check (n - b) (b + 4)

good n = check n 2

main = print $ filter (not . good) [1,3..]
