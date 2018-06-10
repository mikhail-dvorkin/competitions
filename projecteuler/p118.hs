module Main where

import Math
import List

search _ [] = [[]]
search [] digs = search' [] 0 (10 ^ (length digs)) digs
search set digs = search' set 0 (head set) digs

search' set value lim digs =
  if (value >= lim) then
    []
  else
    let here = if (isPrime value) then map (value : ) $ search (value : set) digs else [] in
    here ++ concat [search' set (10 * value + x) lim (digs \\ [x]) | x <- digs]

solve = length $ search [] [1..9]