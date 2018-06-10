module Main where

lowFactors n = filter (\x -> n `mod` x == 0) $ takeWhile (\x -> x * x <= n) [1..]

highFactors n = reverse . map (div n) $ lowFactors n

myMerge a b =
  if (last a == head b)
    then (a) ++ (tail b)
    else (a) ++ b

factors n = myMerge (lowFactors n) (highFactors n)

triangle = map (\n -> n * (n + 1) `div` 2) [1..]

main = print $ filter (\n -> (length $ factors $ n) > 500) triangle
