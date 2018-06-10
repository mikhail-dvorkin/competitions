module Main where

import Math

ans 1 sum = sum
ans n sum = ans (n - 1) (sum + phi n)

main = print $ ans 10000 0
