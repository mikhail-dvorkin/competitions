module Main where

import Comb
import List
import Math

good (x, y) = (isSqr (x + y)) && (isSqr $ abs (x - y))

n = 1000

a list = map (\(a, b) -> ((b - a) `div` 2, (b + a) `div` 2)) $ pairs $ map (^2) list

b = (a [1,3..n]) ++ (a [2,4..n])

solve [] = []
solve ((a, b) : list) =
  (solve' $ takeWhile (\(c, d) -> c == a) ((a, b) : list)) ++
  (solve $ dropWhile (\(c, d) -> c == a) list)

solve' x = map (\(a, b) -> (fst $ head x, a, b)) (filter good $ pairs $ map snd x)

main = print $ solve $ sort b
