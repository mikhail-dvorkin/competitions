module Main where

a = 1 : 1 : 1 : 2 : zipWith3 (\a -> \b -> \c -> 2 * a - b + c) (tail $ tail $ tail a) (tail $ tail a) a
