module Main where

import Math

solve a d =
  let a' = a + 6 * d in
  let d' = d + 1 in
  let d'' = d - 1 in
  let a'' = a - 6 * d'' in
  check a [a'', a' - 1, a', a' + 1, a' + 6 * d' - 1] ++
    concat [check (a + i * d) [a'' + i * d'', a' + i * d' - 1, a' + i * d', a' + i * d' + 1] | i <- [1..5]] ++
      check (a' - 1) [a'', a - 1, a, a' + 6 * d' - 2, a' + 6 * d' - 1] ++
        solve a' d'

check a list =
  let pd = length $ filter isPrime $ map abs $ map (+ (-a)) list in
  if (pd >= 3) then [a] else []

s = 0 : solve 2 1

main = print $ 0
