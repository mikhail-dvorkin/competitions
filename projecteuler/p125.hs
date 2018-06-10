module Main where

import List
import Math

cons n = con n 5 1 3

con n sum a b =
  if (sum == n) then
    True
  else if (sum < n) then
    con n (sum + b * b) a (b + 1)
  else if (b == a + 2) then
    False
  else
    con n (sum - a * a) (a + 1) b

main = print $ sum $ filter cons $ filter palindrome [1..10^8]