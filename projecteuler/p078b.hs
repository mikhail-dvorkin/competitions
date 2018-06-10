module Main where

m = 1000000

genPentaArgs = concat [[n, -n] | n <- [1..]]

genPenta = map (\n -> n * (3 * n - 1) `div` 2) genPentaArgs

signs = cycle [1, 1, -1, -1]

calc _ _ _ [] cum = cum
calc seen (a:args) (s:signs) (l:list) cum =
  if (seen == a)
    then calc (seen + 1) args signs list ((cum + s * l) `mod` m)
    else calc a (a:args) (s:signs) (drop (a - seen - 1) list) cum

ways = 1 : [calc 1 genPenta signs (reverse $ take n ways) 0 | n <- [1..]]

main = print $ takeWhile (\(a, b) -> b > 0) $ zip [0..] ways
