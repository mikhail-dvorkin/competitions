module Main where

f 1 = 1
f n | even n = (n `div` 2)
    | otherwise = (3 * n + 1)

op 1 = 1
op n = 1 + (op $ f n)

greater (a, fa) (b, fb) =
  if (fa >= fb)
    then (a, fa)
    else (b, fb)

argmaxPair op (a:[]) = (a, op a)
argmaxPair op (a:as) = greater (a, op a) (argmaxPair op as)

argmax op a = fst $ argmaxPair op a

main = print $ argmaxPair op [200000..999999]
--main = print $ argmaxPair op [100000..199999]
--156159,383