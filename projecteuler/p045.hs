module Main where

import Math

mysqrt = floor . sqrt. fromInteger

isTriangle x =
  let n = mysqrt (2 * x)
  in 2 * x == n * (n + 1)

common (a:as) (b:bs) =
  if (a < b)
    then common as (b:bs)
    else if (a > b)
      then common (a:as) bs
      else a : common as bs

main = print $ common (common (ngonal 5) (ngonal 6)) (ngonal 3)
