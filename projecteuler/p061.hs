module Main where

import Math
import List

gonals g list = length $ filter (not . disjointSorted list) [ngonal n | n <- [3..(g + 2)]]

gg g = foldr mergeSorted [] $ map ngonal [3..(g + 2)]

isGonal g value = elemSorted value $ gg g

make [] = []
make list = [100 * a + b | (a, b) <- zip (init list) (tail list)]

good g list =
  let mk = make list
  in if (length list == g)
    then let newlist = list ++ [head list] in (isGonal g $ last $ make newlist) && good g newlist
    else (isGonal g $ head $ mk ++ [1]) && (gonals g $ sort $ mk) >= length mk

solve g have =
  if (good g have)
    then if (length have == g)
      then [(have, 101 * sum have)]
      else concat [solve g (x:have) | x <- [(last $ 10 : have)..99]]
    else []

main = print $ solve 6 []
