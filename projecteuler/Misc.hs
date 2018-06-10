module Misc where

import List

collect [] = []
collect (a:list) = collect' a 1 list

collect' a n [] = [(a, n)]
collect' a n (b:list) =
  if (a == b)
    then collect' a (n+1) list
    else (a, n) : collect (b:list)

myNub [] = []
myNub (a:as) = a : myNub (dropWhile (== a) as)
