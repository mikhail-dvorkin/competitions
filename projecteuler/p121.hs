module Main where

import Comb

main = print $ (product [1..16]) `div` (sum $ map product $ concat $ map (flip subsets [1..15]) [0..((15 - 1) `div` 2)])
