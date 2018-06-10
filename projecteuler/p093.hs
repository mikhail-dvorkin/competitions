module Main where

import Comb
import List
import Math
import Misc

obtain [] = []
obtain (x:[]) = [x]
obtain list =
  myNub $ sort $
  concatMap (\(list1, list2) -> combine (obtain list1) (obtain list2)) $
  filter (\(list1, list2) -> (not $ null list1) && (not $ null list2)) $
  parts list

parts [] = [([], [])]
parts (l:list) =
  let p = parts list in
  map (\(a, b) -> (l:a, b)) p ++ map (\(a, b) -> (a, l:b)) p

combine v1 v2 = concat [ops a b | a <- v1, b <- v2]

ops a b = [a + b, a - b, a * b, a / b]

goodness list =
  let values = obtain list in
  head $ dropWhile (flip elem values) [1..]

main = print $ stairMax goodness 0 $ subsets' 4 [1..]
