module Main where

value 'I' = 1
value 'V' = 5
value 'X' = 10
value 'L' = 50
value 'C' = 100
value 'D' = 500
value 'M' = 1000

len 0 = 0
len 1 = 1
len 2 = 2
len 3 = 3
len 4 = 2
len 5 = 1
len 6 = 2
len 7 = 3
len 8 = 4
len 9 = 2
len n = (len $ n `mod` 10) + (len $ n `div` 10) + (if n >= 4000 then 2 else 0)

parse [] = 0
parse (a:[]) = value a
parse (a:b:c) =
  let aa = value a in
  let bb = value b in
  if (aa < bb) then
    - aa + bb + parse c
  else
    aa + parse (b:c)

solve s = (length s) - (len $ parse s)

main = do
  s <- readFile "data\p89data"
  print $ sum $ map solve $ lines s
