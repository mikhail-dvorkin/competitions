module Main where

import Math

solve n = sum [(cnk (n - x) x) + (cnk (n - 2*x) x) + (cnk (n - 3*x) x) | x <- [1..n]]
