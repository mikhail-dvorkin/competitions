module Main where

import Math
import Misc

good n = 89 == (head $ dropWhile (flip notElem [1, 89]) (t n))

t = iterate (sum . map (^ 2) . digits)

main = print $ length $ filter good [1..9999999]