module Main where

import List
import Math
import Misc

s = map count [0..]
  where
    count 0 = 0
    count 1 = 0
    count 23 = 6
    count 43 = 7
    count 59 = 8
    count 77 = 8
    count 83 = 8
    count 107 = 9
    count 149 = 9
    count 163 = 9
    count 179 = 10
    count n = minimum $ [1 + (s !! (n - 1))] ++
      if (even n) then [1 + (s !! (n `div` 2))] else [] ++
      [(s !! x) + (s !! (n `div` x)) | x <- tail $ lowFactors n]

next a = myNub $ sort $ filter (> head a) [x + y | x <- a, y <- a]

search a =
  if (length a > 11) then [] else
  if (s !! (head a) + 1) > length a then [a] else [] ++
  concat [search (b : a) | b <- next a]

solve = sum [s !! n | n <- [1..200]]