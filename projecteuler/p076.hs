module Main where

ways n = ways' (n - 1) !! n

ways' 0 = 1 : [0,0..]
ways' mx = 
  let prev = ways' (mx - 1)
  in let a = zipWith (+) prev (take mx [0,0..] ++ a)
  in a

{-ways' sum mx
 | sum < 0    = 0
 | mx == 1    = 1
 | otherwise  = (ways' (sum - mx) mx) + (ways' sum (mx - 1))-}

main = print $ 0
