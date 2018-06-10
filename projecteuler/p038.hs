module Main where

import Math

good3 a n digs =
  if (length digs < 9)
    then good3 a (n + 1) (digits (a * n) ++ digs)
    else if (length digs > 9)
      then False
      else pandigital digs

good a = good3 a 1 []

main = print $ filter good [1..]
