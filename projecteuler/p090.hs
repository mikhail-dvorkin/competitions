module Main where

import Comb
import List

s = subsets 6 [0..9]

sq = map (\n -> (n `div` 10, n `mod` 10)) [n^2 | n <- [1..9]]

obtain (a, b) (x, y) =
  (obtain' a x && obtain' b y) || (obtain' a y && obtain' b x)

obtain' a x = elem x a || (x == 6 || x == 9) && elem (15 - x) a

good (a, b) = all (obtain (a, b)) sq

main = print $ length $ nub $ filter good $ [(a, b) | a <- s, b <- s, a <= b]