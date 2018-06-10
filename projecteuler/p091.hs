module Main where

import Math

solve n = n * n + (sum $ map (s n) [(x, y) | x <- [0..n], y <- [0..n], x + y > 0])

s n (x, y) =
  let g = gcd x y in
  t n x y (y `div` g) (-x `div` g) +
  t n x y (-y `div` g) (x `div` g)

t n x y dx dy = length $ takeWhile (\t -> inside n (x + dx * t) (y + dy * t)) [1..]

inside n x y = all (>= 0) [x, y] && all (<= n) [x, y]

main = print $ solve 50