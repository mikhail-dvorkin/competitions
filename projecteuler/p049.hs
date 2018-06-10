module Main where

import Math
import List

perm a b = (sort $ digits a) == (sort $ digits b)

good a b =
  let c = (a + b) `div` 2
  in isPrime(c) && perm a b && perm b c

f b = [(a, (a+b) `div` 2, b) | a <- (takeWhile (<b) $ dropWhile (<1000) primes), good a b]

main = print $ foldr (++) [] $ map f $ dropWhile (<1000) primes
