module Main where

import Math

digits100 n = take 100 $ digits $ mysqrt (n*10^200)

main = print $ sum $ map (sum . digits100) $ filter (not . isSqr) [1..100]
