module Main where

import Math

lgood [] = True
lgood digits = (isPrime $ digitsToInt digits) && lgood (tail digits)

rgood [] = True
rgood digits = (isPrime $ digitsToInt digits) && rgood (init digits)

good n =
  let digs = digits n
  in lgood digs && rgood digs

main = print $ sum $ take 11 $ filter good [10..]
