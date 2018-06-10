module Main where

a n = a' where
 a' = (take n $ repeat 1) ++ [2] ++ zipWith3 (\a -> \b -> \c -> 2 * a - b + c) (drop n a') (drop (n - 1) a') a'
