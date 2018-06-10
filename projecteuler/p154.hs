f p n
  | n < p	= 0
  | otherwise	= let n' = n `div` p in n' + f p n'

f2 = map (f 2) [0..]
f5 = map (f 5) [0..]
t2 = f2 !! m
t5 = f5 !! m

m = 1000
mm = 4

a = map (\n -> (f 2 n, f 5 n)) [0..m]

s (x, y, z) = min (t2 - (f2 !! x) - (f2 !! y) - (f2 !! z)) (t5 - (f5 !! x) - (f5 !! y) - (f5 !! z))

solve = length $ filter (>= mm) [s (x, y, m - x - y) | x <- [0..(m `div` 3)], y <- [x..((m - x) `div` 2)]]