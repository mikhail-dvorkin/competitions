module Main where

import Math

s p q =
  let t = 10 ^ (digitNumber p) in
  let n = (q - p) * (modInverse q t) `mod` q in
  n * t + p

p = drop 2 $ zip primes $ tail primes

main = print $ sum $ map (\(p, q) -> s p q) $ takeWhile (\(p, q) -> p < 1000000) p
