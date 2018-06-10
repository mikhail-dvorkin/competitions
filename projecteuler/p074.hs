module Main where

import Math

f n = sum $ map factorial $ digits n

op n = op' n [] 0
op' n list sum =
  if elem n list
    then sum
    else op' (f n) (n:list) (sum + 1)

main = print $ 0