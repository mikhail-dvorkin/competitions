module Main where

m = 10000000000

two 0 = 1
two n =
  if (even n) then
    let x = two (n `div` 2) in
    x * x `mod` m
  else
    let x = two (n - 1) in
    2 * x `mod` m

main = print $ (28433 * (two 7830457) + 1) `mod` m
