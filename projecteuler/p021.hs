module Main where

lowFactors n = filter (\x -> n `mod` x == 0) $ takeWhile (\x -> x * x <= n) [1..]

highFactors n = reverse . map (div n) $ lowFactors n

myMerge a b =
  if (last a == head b)
    then (a) ++ (tail b)
    else (a) ++ b

factors n = myMerge (lowFactors n) (highFactors n)

d = sum . init . factors

isAmicable n =
  let m = d n
  in (m /= n && n == d m)

main = print $ sum $ filter isAmicable [2..10000]