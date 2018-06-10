module Main where

import Math
import List

powers a = b where
  b = a : map (* a) b

t a b = filter (\x -> x > 9 && digitSum x == a) (take b $ powers a)

s a b = sort $ concat [t i b | i <- [1..a]]

solve = (s 100 100) !! 29