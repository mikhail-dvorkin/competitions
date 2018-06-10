module Main where

p = map (\n -> n * (3 * n - 1) `div` 2) [1..]

contains a (p:ps) =
  if (p > a)
    then False
    else if (p == a)
      then True
      else contains a ps

isp a = contains a p

good c d = isp (d - c) -- && isp (2 * c - d)

goods d = [2 * c - d | c <- (dropWhile (\c -> 2 * c <= d) $ takeWhile (<d) $ p), good c d]

main = print $ filter isp $ foldr (++) [] $ [goods b | b <- drop 1000 p]
