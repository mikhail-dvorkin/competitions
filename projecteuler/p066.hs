module Main where

import Math

good d x =
  let y = mysqrt $ (x*x - 1) `div` d
  in (x*x - d*y*y == 1)

solve d = head $ filter (good d) [2..]


main = print $ p66 1000
