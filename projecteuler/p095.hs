module Main where

import Array
import Math

n = 1000000

white = 0
black = -1

mark = array (-1, n) ((-1, 0) : (0, 0) : [(i, white) | i <- [1..n]])

dd 1 = 1
dd n = d n

dfs num v mark
  | v > n		= mark
  | mark ! v == black   = mark
  | mark ! v == white   =
      let mark' = mark // [(v, num)] in
      let mark'' = dfs (num + 1) (d v) mark' in
      mark'' // [(v, black)]
  | otherwise		=
      let ans = mark ! 0 in
      let cur = num - mark ! v in
      if (cur > ans) then
        mark // [(0, cur), (-1, v)]
      else
        mark

ans n = ans' n n (dd n)
ans' res fin cur
  | fin == cur	= res
  | otherwise	= ans' (min res cur) fin (dd cur)

solve = foldr (dfs 1) mark [1..n]

main = print $ ans $ solve ! (-1)
