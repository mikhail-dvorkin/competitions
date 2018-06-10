module Main where

import ContFrac
import Math

a = map (flip convergent $ contFrac 2) [1..]

b = map (\(a, b) -> (a + 1) `div` 2) a

f a = mysqrt $ a * (a - 1) `div` 2

good a =
  let b = f a in
  a * (a - 1) == 2 * b * (b + 1)
  
main = print $ sum $ takeWhile (> 0) $ map (\p -> 100000000 `div` p) $ map (\b -> b + (b - 1) + mysqrt (b ^ 2 + (b - 1) ^ 2)) $ tail $ filter good b
