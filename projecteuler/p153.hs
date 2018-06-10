module Main where

import Array
import List
import Math

list 1 = [0..]
list n = listRelPrime n

sumSigma = cumSum (map sigma [1..])

z ((a, x) : []) = [(a, x)]
z ((a, x) : (b, y) : c) =
  if (a == b) then
    z ((a, x + y) : c)
  else
    (a, x) : z ((b, y) : c)

foldl' f z []     = z
foldl' f z (x:xs) = let z' = z `f` x 
                    in seq z' $ foldl' f z' xs

sumdiv n =
  if (n > 200000) then
    n * n - foldl' (+) 0 [n `mod` a | a <- [1..n]]
  else
    sumSigma !! fromInteger (n - 1)

count list = sum [(sumdiv v) * c | (v, c) <- list]

solve n = foldl1 (\a b -> z $ sort (a ++ b)) [solve' n a | a <- takeWhile (\a -> a * a <= n) [1..]]

solve' n a = [solve'' n a b | b <- takeWhile (\b -> a * a + b * b <= n) (list a)]

solve'' n a b = (n `div` (a * a + b * b), a * (if (b > 0) then 2 else 1))

main = print $ count $ solve $ 10^8