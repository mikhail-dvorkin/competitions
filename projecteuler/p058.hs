module Main where

import Math

a = cycle [2, 0, 0, 0]
b = cumSum a
c = 1 : (map (+1) $ cumSum b)

frac = zip (cumProp isPrime c) [1..]

good (a, b) = (10 * a < b) && (b `mod` 4 == 1)

main = print $ take 2 $ filter good frac
