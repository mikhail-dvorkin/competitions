module Main where

import Math

sols n = length $ lowFactors (n * n)

s list = product $ map (\i -> 2 * i + 1) list

v list = product $ zipWith (^) primes list

search d upto list =
  let ss = s list in
  if (ss > d) then
    v list
  else
    minimum [search d x (list++[x]) | x <- [1..upto]]
