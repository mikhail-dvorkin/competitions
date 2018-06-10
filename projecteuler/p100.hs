module Main where

import ContFrac
import Math

a = map (flip convergent $ contFrac 2) [1..]

b = map (\(a, b) -> (a + 1) `div` 2) a

f a = mysqrt $ a * (a - 1) `div` 2

good a =
  let b = f a in
  a * (a - 1) == 2 * b * (b + 1)
  
main = print $ take 20 $ map (\b -> (b, f b)) $ filter good b
