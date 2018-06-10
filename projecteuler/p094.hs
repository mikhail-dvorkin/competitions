module Main where

import Math

good1 a = isSqr $ (3 * a + 1) * (a - 1)
good2 a = isSqr $ (3 * a + 2) * (a + 2)

g1 a = isSqr $ 3 * a * a + 4
g2 a = isSqr $ 3 * a * a - 2

a1 = map (\n -> 3 * n * n + 4) $ filter g1 [1..]
a2 = map (\n -> 6 * n * n - 2) $ filter g2 [1..]

main = print $ 0
