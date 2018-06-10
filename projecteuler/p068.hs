module Main where

import Comb
import List
import Math

valid list = (sum list) `mod` (length list) == 0

getsum list =
  let n = length list
  in (sum list) `div` n + 2 * n + 1

triple s a b = [s - a - b, a, b]

pairs list = pairs' (getsum list) list
pairs' s list = (triple s (last list) (head list)) : pairs'' s list
pairs'' _ [] = []
pairs'' _ (a:[]) = []
pairs'' s (a:b:c) = (triple s a b) : pairs'' s (b:c)

isFirst list = sort list == [1..(length list)]

good list = (valid list) && isFirst (list ++ (map head $ pairs list))

solve n = map (concatenateList . map toInteger . concat . lexCycleFirst . pairs) $ filter good $ subcycles n [1..2*n]

main = print $ exactlyDigits 16 $ sort $ solve 5
