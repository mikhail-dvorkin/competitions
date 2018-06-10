module Main where

import Math

range d = [(d `div` 3)+1..((d-1) `div` 2)]

good d = length $ filter (relPrime d) (range d)

main = print $ sum [good d | d <- [1..10000]]