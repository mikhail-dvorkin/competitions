module Main where

import Math

main = print $ argmax (\n -> (fromInteger n) / (fromInteger $ phi n)) [1..1000000]
