module Main where

import Math
import List

sumLE p (a, b, c) = a + b + c <= p

pyth3 limit v u = takeWhile limit [(n*(u*u-v*v), n*(2*u*v), n*(u*u+v*v)) | n <- [1..]]

pyth2 limit v = foldr (++) [] $ takeWhile (not . null) [pyth3 limit v u | u <- [(v+1),(v+3)..]]

pyth limit = foldr (++) [] $ takeWhile (not . null) [pyth2 limit v | v <- [1..]]

sum3 (a, b, c) = a + b + c

main = print $ sort $ map sum3 $ pyth (sumLE 1000)
