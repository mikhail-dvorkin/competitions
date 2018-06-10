module Main where

import Math

frac n = digits n ++ frac (n + 1)

f = 0 : frac 1

main = print . product. map(f !! ) . map (10^) $ [0..6]
