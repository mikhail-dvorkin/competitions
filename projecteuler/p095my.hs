module Main where

import Math
import MyArray

n = 1000000

white = 0
black = -1

mark = makeMyArray (n + 2)

dd 1 = 1
dd n = fromInteger $ d $ toInteger n

dfs num v mark
  | v > n		= mark
  | get v mark == black	= mark
  | get v mark == white	= set black v $ dfs (num + 1) (dd v) (set num v mark)
  | otherwise		=
      let ans = get 0 mark in
      let cur = num - (get v mark) in
      if (cur > ans) then
        set v (n + 1) $ set cur 0 $ mark
      else
        mark

ans n = ans' n n (dd n)
ans' res fin cur
  | fin == cur	= res
  | otherwise	= ans' (min res cur) fin (dd cur)

solve = foldr (dfs 1) mark [5916] --[1..n]

main = print $ ans $ get (n + 1) solve
