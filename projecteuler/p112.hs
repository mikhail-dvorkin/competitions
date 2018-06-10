module Main where

import Math

inc [] = True
inc (a:[]) = True
inc (a:b:c) = (a <= b) && inc (b:c)

dec [] = True
dec (a:[]) = True
dec (a:b:c) = (a >= b) && dec (b:c)

bouncy n =
  let digs = digits n in
  (not $ inc digs) && (not $ dec digs)

main = print $ head $ dropWhile (\(a, b) -> 100 * a < 99 * b) $ getFraction bouncy [1..]
