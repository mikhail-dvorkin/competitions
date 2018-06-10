module Main where

import List
import Math

fib = 1 : 1 : zipWith (+) fib (tail fib)

good a =
  let d = digits a in
  (length d >= 9) && (good' $ take 9 d) && (good' $ drop (length d - 9) d)

good' list = sort list == [1..9]

solve (a1, a2) (b1, b2) n =
  if (a1 > 1000000000) then
    solve (a1 / 10, a2) (b1 / 10, b2) n
  else if (good' $ digits a2) && (good' $ digits $ floor a1) then
    (a1, a2, n)
  else
    solve (b1, b2) (a1 + b1, (a2 + b2) `mod` 1000000000) (n + 1)

main = print $ solve (1, 1) (1, 1) 1
-- head $ filter (\(f, n) -> good f) $ zip fib [1..]