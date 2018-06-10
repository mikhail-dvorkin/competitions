module Main where

m = 1000000

ways = 1 : [flip mod m $ sum [(-1)^(k+1) * (p (n - k * (3 * k - 1) `div` 2) + p (n - k * (3 * k + 1) `div` 2)) | k <- [1..n]] | n <- [1..]]
  where p n = if n < 0 then 0 else ways !! n

main = print $ takeWhile (\(a, b) -> b > 0) $ zip [0..] ways
