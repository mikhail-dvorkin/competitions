module Main where

a = 25 : 50 : [1..20] ++ [2,4..40] ++ [3,6..60]
b = 50 : [2,4..40]

takeUpto _ [] = [[]]
takeUpto 0 as = [[]]
takeUpto n (a:as) = (map (a:) $ takeUpto (n - 1) (a:as)) ++ takeUpto n as

checkouts = [x : y | x <- b, y <- takeUpto 2 a]

main = print $ length $ filter ((< 100) . sum) checkouts
