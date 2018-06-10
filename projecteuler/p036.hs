module Main where

import Math

t 0 _ = []
t n base = (n `mod` base) : t (n `div` base) base

good n = palindrome (t n 10) && palindrome (t n 2)

main = print $ cumSum $ filter good [1..999999]
