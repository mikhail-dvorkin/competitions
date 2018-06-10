module Main where

coins = [1,2,5,10,20,50,100,200]

ways [] = 1:[0,0..]
ways (coin:coins) = newways
  where
    newways = zipWith (+) (take coin [0,0..] ++ newways) (ways coins)

main = print $ ways coins !! 200
