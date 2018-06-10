module Main where

import Math

lim = 100
n = 1000000

dd 1 = 1
dd n = d n

a n = attempt lim 1 n (dd n)

c n = cycle' $ findRep n (dd n)

findRep a b
  | a == b	= a
  | b > n       = 1
  | otherwise	= findRep (dd a) (dd $ dd b)

cycle' 1 = 0
cycle' n = cycle'' 1 n (dd n)
cycle'' steps fin cur
  | fin == cur	= steps
  | otherwise	= cycle'' (steps + 1) fin (dd cur)


attempt lim steps fin cur
  | cur == 1		= 0
  | cur == fin		= steps
  | steps == lim	= lim + fin
  | otherwise		= attempt lim (steps + 1) fin (dd cur)

main = print $ stairMax c 0 [1..50000]