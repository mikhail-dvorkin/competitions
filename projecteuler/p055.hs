module Main where

import Math

isLychrel n = isLychrel' (n + digitReverse n) 50

isLychrel' _ 0 = True
isLychrel' n iter =
  if palindrome n
    then False
    else isLychrel' (n + digitReverse n) (iter - 1)

main = print $ length $ filter isLychrel [1..9999]
