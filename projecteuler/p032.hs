module Main where

import List

pandigital s = (length s == 9 && length (nub s) == 9 && head (sort s) == '1')

isGood a b = pandigital $ show a ++ show b ++ show (a * b)

good23 = [a * b | a <- [10..99], b <- [100..999], isGood a b]
good14 = [a * b | a <- [1..9], b <- [1000..9999], isGood a b]

main = print $ sum $ nub $ good23 ++ good14
