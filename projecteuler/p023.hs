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

isAbundant n = (d n > n)

abundant = filter isAbundant [1..]

merge (a:x) (b:y) =
  if a < b
    then merge x (b:y)
    else if a > b
      then b : merge (a:x) y
      else a : merge x y

notTwoAbundants n = null $ filter (\m -> (isAbundant (n - m))) $ takeWhile (<n) abundant

main = print $ sum $ filter notTwoAbundants [1..28123]
