module Main where

import Math

check 3 = []
check p = concat [check' p x | x <- [1..(p `div` 3)], isSqr (x * (4 * p - 3 * x))]

check' p x =
  let d = x * x * x * (4 * p - 3 * x) in
  let n1 = (3 * x * x + mysqrt d) in
  let n2 = 2 * (p - 3 * x) in
  let n = n1 `div` n2 in
  if (n1 `mod` n2 == 0) then [(p, n, x)] else []

s = concat $ map check $ primes

cubes = map (^ 3) [1..]

cand = zipWith (-) (tail cubes) cubes

main = print $ length $ filter isPrime $ takeWhile (< 1000000) cand
