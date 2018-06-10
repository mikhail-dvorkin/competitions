module Main where

ways n = ways' n !! n

ways' 0 = 1 : [0,0..]
ways' mx = 
  let prev = ways' (mx - 1)
  in let a = zipWith (+) prev (take mx [0,0..] ++ a)
  in a

main = print $ takeWhile (\(a, b) -> b > 0) [(n, (ways n) `mod` 1000000) | n <- [0..]]
