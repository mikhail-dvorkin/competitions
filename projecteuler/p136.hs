module Main where

import Math

divs n = length $ takeWhile (\z -> z * z < 3 * n) (factors n)

s n
  | n `mod` 4 == 3	= divs $ n
  | n `mod` 8 == 4	= divs $ n `div` 4
  | n `mod` 16 == 0	= divs $ n `div` 16
  | otherwise		= 0

p = 1 : tail primes

c = [filter (\n -> n `mod` 4 == 3) p, map (* 4) p, map (* 16) p]

solve = concat $ map (takeWhile (< 50000000)) c

main = print $ length solve
