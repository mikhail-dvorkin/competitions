module Main where

nonleap = [31,28,31,30,31,30,31,31,30,31,30,31]
leap = [31,29] ++ drop 2 nonleap
months = cycle $ nonleap ++ nonleap ++ nonleap ++ leap

nxt a b = (a + b) `mod` 7

firstdays = 2 : zipWith nxt firstdays months

main = print $ length $ filter (6==) $ take 1200 firstdays
