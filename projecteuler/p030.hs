module Main where

sumdeg deg n = sum . map (\c -> read [c] ^ deg) $ show n

good deg n = (n == sumdeg deg n)

p30 = filter (good 5) [10..]

cumSum (a:as) = a : map (+a) (cumSum as)

main = print $ cumSum p30
