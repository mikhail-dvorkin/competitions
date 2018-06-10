module Main where

import List
import Math

rad = product . primeFactors

solve = (0, 0) : (sort $ map (\n -> (rad n, n)) [1..100000])

main = print $ solve !! 10000