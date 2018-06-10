module Main where

import Comb
import List

good set = alldiff $ sort $ map sum $ allsubsets set

good2 set = (set !! 0 + set !! 1 > last set) &&
  (set !! 0 + set !! 1 + set !! 2 > last set + last (init set))

alldiff [] = True
alldiff [a] = True
alldiff (a:b:c) = (a /= b) && alldiff (b:c)

solve n range = sort $ map (\set -> (- (sum set), set)) $
  filter good $ filter good2 $ subsets n range

main = print $ solve 2 [1..10]