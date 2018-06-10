module Main where

import Math

s n =
  let p = primes !! (n - 1) in
  (2 * (toInteger n) `mod` p) * p

solve = head $ dropWhile (\n -> s n <= 10^10) [1..]