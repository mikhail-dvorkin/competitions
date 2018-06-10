module Main where

import Math

sols n = length $ lowFactors (n * n)

-- sols n = length $ filter (good n) [(n+1)..2*n]
-- good n x = (n * x) `mod` (x - n) == 0

main = print $ stairMax sols 0 [1..]
-- until (\n -> sols n > 1000) (+1) 1000
