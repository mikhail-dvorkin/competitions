module Main where

--[9,10404,16900,97344,576081,6230016,7322436,12006225,36869184,37344321,70963776,196112016,256160025,1361388609,1380568336,8534988225,9729849600,12551169024,13855173264,16394241600,123383587600,142965659664,547674002500]

import List
import Math

limit = 1000000000000
get = takeWhile (<= limit)

squares = [n*n | n <- [1..]]
cubes = [n*n*n | n <- [1..]]

check = concat [check' p | p <- filter (\p -> p == root p) [1..(mysqrt $ limit `div` 8)]]
check' p = concat [check'' p q | q <- [1..(mysqrt $ mysqrt $ limit `div` (p * p))]]
check'' p q = get [p * q * q + p * p * q * r | r <- drop (fromInteger q) cubes]

ans = sum $ filter isSqr $ check
