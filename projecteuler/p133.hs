module Main where

import Math

a n = a' n 0 0

a' n len r =
  if (r == 0) && (len > 0) then
    len
  else
    a' n (len + 1) ((r * 10 + 1) `mod` n)

repunit p 0 = (0, 1)
repunit p n =
  if (odd n) then
    let (a, b) = repunit p (n - 1) in
      ((10 * a + 1) `mod` p, (10 * b) `mod` p)
  else
    let (a, b) = repunit p (n `div` 2) in
      ((b * a + a) `mod` p, (b * b) `mod` p)

t = filter (\p -> 0 == (fst $ repunit p 1000000000)) primes

good [] = True
good (2 : list) = good list
good (5 : list) = good list
good _ = False

can 2 = False
can 5 = False
can p = good $ primeFactors $ a p

main = print $ sum $ filter (not . can) $ takeWhile (< 100000) primes