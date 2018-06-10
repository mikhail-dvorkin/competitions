module Main where

import List
import Math
import Misc

search' n = myNub $ sort $ search 0 n n (tail $ factors n)

search nd sum nn [] = []
search nd sum 1 _ = [nd + sum]
search nd sum nn (f:factors) =
  (search nd sum nn factors) ++
  if (nn `mod` f == 0) then
    search (nd + 1) (sum - f) (nn `div` f) (f:factors)
  else
    []

solve [] _ = []
solve list n =
  let (good, list') = solve' False [] list (search' n) in
  (if good then [n] else []) ++ solve list' (n + 1)

solve' good list' [] _ = (good, list')
solve' good list' list [] = (good, list' ++ list)
solve' good list' (l:list) (a:as) =
  if (l == a) then solve' True list' list as else
  if (l < a) then
    solve' good (list' ++ [l]) list (a:as)
  else
    solve' good list' (l:list) as

main = print $ sum $ solve [2..12000] 1
