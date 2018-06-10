module Main where

import Math

f n = 5 * n * n + 2 * n + 1

next n = head $ filter (isSqr . f) [(n * 68541019 `div` 10000000)..]

main = print $ iterate next 2
