module Main where

import List
import Math

a n = a' n 0 0

a' n len r =
  if (r == 0) && (len > 0) then
    len
  else
    a' n (len + 1) ((r * 10 + 1) `mod` n)

list = 1 : 3 : 7 : 9 : map (+ 10) list

good = filter (not . isPrime) $ filter (\n -> (n - 1) `mod` (a n) == 0) $ tail list

main = print $ sum $ take 25 good
