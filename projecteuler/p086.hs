module Main where

import List
import Math
import Misc

sumLE p (a, b, c) = a + b + c <= p

pyth3 limit v u =
  if relPrime v u
    then takeWhile limit [(n*(u*u-v*v), n*(2*u*v), n*(u*u+v*v)) | n <- [1..]]
    else []

pyth3' limit v u = takeWhile limit [(n*(u*u-v*v), n*(2*u*v), n*(u*u+v*v)) | n <- [1..]]

pyth2 limit v = foldr (++) [] $ takeWhile (not . null) [pyth3' limit v u | u <- [(v+1),(v+3)..], relPrime v u]

pyth limit = foldr (++) [] $ takeWhile (not . null) [pyth2 limit v | v <- [1..]]

sum3 (a, b, c) = a + b + c

cubes (a, b) n = if (a > n) then 0 else
  if (a >= b)
    then b `div` 2
    else max 0 (a - ((b + 1) `div` 2) + 1)

c n (a, b, _) = cubes (a, b) n + cubes (b, a) n

cubes' (a, b) n = if (a > n) then [] else
  if (a >= b)
    then [(x, b - x, a) | x <- [1..b `div` 2]]
    else [(b - x, x, a) | x <- [((b + 1) `div` 2)..a]]

c' n (a, b, c) = cubes' (a, b) n ++ cubes' (b, a) n

f n = sum $ map (c n) $ pyth (sumLE (60 * n))

main = print $ pyth (sumLE 100)
