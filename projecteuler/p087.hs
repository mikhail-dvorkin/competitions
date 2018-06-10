module Main where

import List
import Math
import Misc

primesP power = map (\n -> n ^ power) primes

a n = takeWhile (< n) $ primesP 2
b n = takeWhile (< n) $ primesP 3
c n = takeWhile (< n) $ primesP 4

s n = collect $ takeWhile (< n) $ sort [x + y + z | x <- a n, y <- b n, z <- c n]

main = print $ length $ s 50000000
