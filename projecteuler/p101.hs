module Main where

p = [n ^ 3 | n <- [1..]]
q = [sum [(-n) ^ i | i <- [0..10]] | n <- [1..]]

approx [a] = repeat a
approx a =
  let b = approx $ zipWith (-) (tail a) (init a) in
  fold (head a) b

fold a (b : c) = a : fold (a + b) c

solve p n = (approx $ take n p) !! n

main = print $ sum $ map (solve q) [1..10]
