module Main where

a n = a' n 0 0

a' n len r =
  if (r == 0) && (len > 0) then
    len
  else
    a' n (len + 1) ((r * 10 + 1) `mod` n)

list' = 1 : 3 : 7 : 9 : map (+ 10) list'
list = map (+ 1000000) list'

main = print $ head $ dropWhile (\(n, v) -> v <= 1000000) $ map (\n -> (n, a n)) list
