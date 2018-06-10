module Main where

import Math

repunit p 0 = (0, 1)
repunit p n =
  if (odd n) then
    let (a, b) = repunit p (n - 1) in
      ((10 * a + 1) `mod` p, (10 * b) `mod` p)
  else
    let (a, b) = repunit p (n `div` 2) in
      ((b * a + a) `mod` p, (b * b) `mod` p)

t = filter (\p -> 0 == (fst $ repunit p 1000000000)) primes

solve = sum $ take 40 t
