module Main where

import Math
import List

value _ [] = 0
value v ((-1):as) = 10 * (value v as) + v
value v (a:as) = 10 * (value v as) + a

pp list =
  let f = if (last list == -1) then 1 else 0
  in filter isPrime [value v list | v <- [f..9]]

count = length . pp

first list = (head $ pp list, list)

lists 0 = [[]]
lists n = [a:as | a <- [-1..9], as <- lists (n - 1)]

allLists = concat [lists n | n <- [1..]]

isGoodList list = last list /= 0 && head (sort list) == -1

goodLists = filter isGoodList allLists

p51 m = map first $ filter (\list -> count list >= m) goodLists

main = print $ 0
