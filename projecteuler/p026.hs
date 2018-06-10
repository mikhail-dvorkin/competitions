module Main where

p 0 n = 0
p 1 n = 1
p a n = 1 + p (10 * a `mod` n) n

period n =
  if (n `mod` 2 == 0)
    then period (n `div` 2)
    else if (n `mod` 5 == 0)
      then period (n `div` 5)
      else p (10 `mod` n) n

greater (a, fa) (b, fb) =
  if (fa >= fb)
    then (a, fa)
    else (b, fb)

argmaxPair op (a:[]) = (a, op a)
argmaxPair op (a:as) = greater (a, op a) (argmaxPair op as)

argmax op a = fst $ argmaxPair op a

main = print $ argmax period [1..1000]
