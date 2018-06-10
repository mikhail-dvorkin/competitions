module Main where

import Math

contains a [] = False
contains a (b:bs) = (a == b) || contains a bs

dd = [1,2,3,5,7,11,13,17]

find a =
  if length a > 0 && contains (head a) (tail a)
    then []
    else if length a >= 3 && ((100 * (a !! 0) + 10 * (a !! 1) + (a !! 2)) `mod` (dd !! (10 - length a)) > 0)
      then []
      else if (length a == 10)
        then [digitsToInt a]
        else foldr (++) [] [find (x:a) | x <- [0..9]]

main = print $ sum $ find []
