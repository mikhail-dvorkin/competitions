module Main where

import List
import Misc
import Math

cubes = map (^3) [1..]

findSuchCube digs = head $ filter (\c -> digs == (sort . digits) c) cubes

solve' size len = map fst $ filter (\(a, b) -> b == size) $ collect $ sort $ map (sort . digits) $ exactlyDigits len cubes

solve size = concat [solve' size len | len <- [1..]]

main = print $ cumOp min (10^1000) $ map findSuchCube $ solve 5
