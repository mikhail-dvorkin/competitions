module Main where

n = 4

twos = 1 : map (* 2) twos

a = take n twos ++ foldr (zipWith (+)) [0,0..] [drop x a | x <- [0..(n-1)]]
