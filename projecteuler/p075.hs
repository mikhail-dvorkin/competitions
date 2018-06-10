module Main where

import List
import Math
import Misc

sumLE p (a, b, c) = a + b + c <= p

pyth3 limit v u =
  if relPrime v u
    then takeWhile limit [(n*(u*u-v*v), n*(2*u*v), n*(u*u+v*v)) | n <- [1..]]
    else []

pyth2 limit v = foldr (++) [] $ takeWhile (not . null) [pyth3 limit v u | u <- [(v+1),(v+3)..]]

pyth limit = foldr (++) [] $ takeWhile (not . null) [pyth2 limit v | v <- [1..]]

sum3 (a, b, c) = a + b + c

solve n = map fst $ filter (\(a, b) -> b == 1) $ collect $ sort $ map sum3 $ ans n

main = print $ sort $ map sum3 $ pyth (sumLE 1000)
