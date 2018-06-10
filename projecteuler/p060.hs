module Main where

import Math

good a b = isPrime (concatenate a b) && isPrime (concatenate b a)

search 0 have _ = [sum have]
search _ _ [] = []
search need have (t:toSearch) = search (need - 1) (t : have) (filter (good t) toSearch) ++ search need have toSearch

p60 m = cumOp min (10^1000) $ concat [(search m [] $ pr (2^n)) | n <- [1..]]

main = print $ p60 5
