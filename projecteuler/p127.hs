module Main where

import Math

limit = 110000

goodc = filter (\c -> radical c < c) [1..limit]

solve = concat [solve' a | a <- [1..limit]]
solve' a = map (\b -> (a, b, a + b)) $ filter (check a) $ map (+ (-a)) $ dropWhile (< 2 * a) goodc

check a b =
  if (gcd a b > 1) then
    False
  else
    let c = a + b in
    (radical a) * (radical b) * (radical c) < c

main = print $ sum $ map (\(a, b, c) -> c) $ solve