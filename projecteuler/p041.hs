module Main where

import Math

good n =
  let digs = digits n
  in isPrime n && npandigital (length digs) digs

main = print $ 0
