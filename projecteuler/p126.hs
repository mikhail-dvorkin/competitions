module Main where

import List
import Misc

cuboid a b c = (cuboid'' b c - cuboid' b c, take a $ repeat $ cuboid' b c)

cuboid' b c =
  if (b == 1) || (c == 1) then
    - b * c
  else
    2 * b + 2 * c - 4

cuboid'' b c =
  if (b == 1) || (c == 1) then
    - b * c
  else
    b * c

pr a
  | a < 0	= 2 * (-a) + 2
  | a > 0	= a + 4

process (bonus, (a:[])) = (bonus, a : pr a : a : [])
process (bonus, (a:list)) = (bonus, a : pr a : process' list)

process' (a:[]) = pr a : a : []
process' (a:list) = pr a : process' list

layer (bonus, list) = 2 * bonus + (sum $ map abs list)

p a b c = map layer $ iterate process $ cuboid a b c

findall limit = concat $ takeWhile (not . null) $ [findall' limit x | x <- [1..]]
findall' limit x = concat $ takeWhile (not . null) $ [findall'' limit x y | y <- [x..]]
findall'' limit x y = concat $ takeWhile (not . null) $ [findall''' limit x y z | z <- [y..]]
findall''' limit x y z = takeWhile (<= limit) $ tail $ p x y z

solve n limit = fst $ head $ dropWhile (\p -> snd p /= n) $ collect $ sort $ findall limit
