module Main where

import Array
import List
import Misc

search' list nn n 1 prod sum = if (prod == sum + n) then (prod, (take n [1,1..]) ++ list) else (2 * nn + 1, [])

search' list nn n m prod sum =
  if (prod > sum + n) || (prod > 2 * nn) then (2 * nn + 1, []) else
  min
    (search' list nn n (m - 1) prod sum)
    (search' (m:list) nn (n - 1) m (prod * m) (sum + m))

search nn n 1 prod sum = if (prod == sum + n) then prod else 2 * nn

search nn n m prod sum =
  if (prod > sum + n) || (prod > 2 * nn) then 2 * nn else
  min
    (search nn n (m - 1) prod sum)
    (search nn (n - 1) m (prod * m) (sum + m))

s n = search (2 * n) n (2 * n) 1 0
s' n = snd $ search' [] (2 * n) n (2 * n) 1 0


relax a index value = a // [(index, min value $ a ! index)]

tt a nn n 1 prod sum = relax a (n + prod - sum) prod
tt a nn n m prod sum =
  if (prod > sum + nn - n) then a else
  let a1 = tt a nn n (m - 1) prod sum in
  tt a1 nn (n + 1) m (prod * m) (sum + m)

initA n = array (1,n) [(m, 2 * m + 1) | m <- [1..n]]

t' n = tt (initA n) n 0 (2 * n) 1 0

t n =
  let a = t' n
  in myNub $ sort $ map (\i -> a ! i) [2..n]

main = print $ sum $ t 12000
