module Main where

import Math

good n = sameDigits (phi n) n

range = filter good [2..999999]

pp n = (fromInteger $ phi n) / (fromInteger $ n)

ans = argmax pp

main = print $ ans range
