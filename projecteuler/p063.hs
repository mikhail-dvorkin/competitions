module Main where

import Math

good n = exactlyDigits n $ map (^n) [1..]

p63 = concat [good n | n <- [1..]]

main = print $ cumSum $ map (\t -> 1) p63
