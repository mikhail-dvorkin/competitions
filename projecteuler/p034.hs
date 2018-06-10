module Main where

import Char
import List
import Numeric
import Math

fac = 1 : map (\n -> product [1..n]) [1..9]

sumfact n = sum $ map (\n -> fac !! n) $ digits n

curious n = (n == sumfact n)

main = print $ cumSum $ filter curious [10..]
