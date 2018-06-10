module Main where

lowFactors n = 1 : (filter (\x -> n `mod` x == 0) $ takeWhile (\x -> x * x <= n) [2..])

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

primes = sieve [ 2.. ]
         where
         sieve (p:x) = p : sieve [ n | n <- x, n `mod` p > 0 ]

pr n = takeWhile (< n) primes

isPrime n = (n > 1) && (null . tail . lowFactors $ n)

qualityAB a b = length $ takeWhile isPrime $ [(n + a) * n + b | n <- [0..]]

greater (a, fa) (b, fb) =
  if (fa >= fb)
    then (a, fa)
    else (b, fb)

argmaxPair op (a:[]) = (a, op a)
argmaxPair op (a:as) = greater (a, op a) (argmaxPair op as)

argmax op a = fst $ argmaxPair op a

main = print $ sum $ filter notTwoAbundants [1..28123]
