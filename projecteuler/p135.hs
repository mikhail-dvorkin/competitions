module Main where

import Math

divs n = length $ takeWhile (\z -> z * z < 3 * n) (factors n)

s n
  | n `mod` 4 == 3	= divs $ n
  | n `mod` 8 == 4	= divs $ n `div` 4
  | n `mod` 16 == 0	= divs $ n `div` 16
  | otherwise		= 0

solve = filter ((== 10) . s) [1..1000000]

main = print $ length solve
