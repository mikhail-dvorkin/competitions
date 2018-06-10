module Main where

import Math

a = 2:5:[x + 2 * y | (x, y) <- zip a (tail a)]

p = [(a, b - a) | (a, b) <- zip a (tail a)]

good (a, b) = digitNumber a < digitNumber b

main = print $ length $ filter good $ take 1000 p
