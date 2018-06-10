module Main where

import Math

fib = 1 : 1 : zipWith (+) fib (tail fib)

getl h =
  let b1 = (h - 1) `div` 2 in
  let b2 = (h + 1) `div` 2 in
  if (isSqr $ h * h + b1 * b1) then
    mysqrt (h * h + b1 * b1)
  else
    mysqrt (h * h + b2 * b2)

s = filter odd $ zipWith (*) fib (tail fib)

main = print $ sum $ map getl $ take 12 $ tail s
