module ContFrac where

import Math

--cf :: Integer -> Integer -> Integer -> Integer -> Integer -> Integer
flr n a b c d =
  if (a * a * n < b * b - 2 * b * c * d + c * c * d * d)
    then (d - 1)
    else flr n a b c (d + 1)

cf n a b c =
  let d = flr n a b c 0
  in if d * d == n
    then [d]
    else d : cf' n (a*c) (c * (c * d - b)) (a * a * n - b * b + 2 * b * c * d - c * c * d * d)

cf' n a b c =
  let g = gcd a $ gcd b c
  in cf n (a `div` g) (b `div` g) (c `div` g)

period' (_:[]) = 0
period' list =
  let mx = maximum $ take 200 $ drop 200 $ list
  in position mx $ tail $ dropWhile (< mx) $ drop 400 list

position value (a:as) =
  if value == a
    then 1
    else 1 + position value as

contFrac n = cf n 1 0 1

contFracE = 2 : concat [[1, 2*n, 1] | n <- [1..]]

convergent _ (a:[]) = (a, 1)
convergent 1 (a:as) = (a, 1)
convergent n (a:as) =
  let (num, den) = convergent (n - 1) as
  in (a * num + den, num)

solvePell d = fst $ head $ filter (\(a, b) -> a * a == d * b * b + 1)[convergent n (contFrac d) | n <- [1..]]

p64 = length $ filter odd $ map (period' . contFrac) [1..10000]

p65 = digitSum $ fst $ convergent 100 contFracE

p66 n = argmax solvePell $ filter (not.isSqr) $ [1..n]
