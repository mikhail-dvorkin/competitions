process row = map (flip mod 7) $ zipWith (+) (0 : row) (row ++ [0])

triangle = iterate process [1]

m = 7

s n = n * (n + 1) `div` 2

count r = (s r) - (count' r m)
count' r a =
  if (r > a) then
    count' r (m * a)
  else if (a == m) then
    0
  else
    let b = a `div` m in
    let t = r `div` b in
    (s t) * (count' b b) + (s $ t - 1) * (s $ b - 1) +
      (t + 1) * (count' (r - t * b) b) + t * ((s $ b - 1) - (s $ (t + 1) * b - r - 1))

ans = count $ 10^9