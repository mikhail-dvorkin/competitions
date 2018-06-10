module Main where

import Math

ways n = ways' (n - 1) !! fromInteger n

ways' 0 = 1 : [0,0..]
ways' mx =
  let prev = ways' (mx - 1)
  in if isPrime mx
    then
      let a = zipWith (+) prev (take (fromInteger mx) [0,0..] ++ a)
      in a
    else prev

main = print $ 0
