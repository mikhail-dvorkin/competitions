module Main where

import Comb
import Math

search _ _ _ (-1) = []
search _ value 0 0 = filter isPrime [value]
search _ _ 0 _ = []
search d value a b =
  if (b > a) then
    []
  else
    let i = if (value == 0) then 1 else 0 in
    concat [search d (10 * value + x) (a - 1) (if (x == d) then b else (b - 1)) | x <- [i..9]]

s a d = head $ dropWhile null [search d 0 a b | b <- [0..a]]

main = print $ sum $ [sum $ s 10 d | d <- [0..9]] 
