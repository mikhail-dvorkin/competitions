module Main where

import Math

f n = 5 * n * n + 14 * n + 1

next n = head $ filter (isSqr . f) [n * 68541019 `div` 10000000..]

a x = (x + 3 * x * x) / (1 - x - x * x)

s = mergeSorted (iterate next 2) (iterate next 5)

main = print $ sum $ take 30 s
