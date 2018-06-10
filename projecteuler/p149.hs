import List

s = 0 : [(100003 - 200003 * k + 300007 * k^3) `mod` 1000000 - 500000 | k <- [1..55]] ++ zipWith (\a -> \b -> (a + b + 1000000) `mod` 1000000 - 500000) (tail s) (drop 32 s)

n = 2000

t = take n $ t' $ tail s
t' s = (take n s) : (t' $ drop n s)

diagonals [row] = map (:[]) row
diagonals (row : rows) =
  let d = diagonals rows in
  let n = length rows in
  (take n d) ++ (zipWith (:) row (drop n d)) ++ [[last row]]

antidiagonals table = diagonals $ map reverse table

list table = concatMap ($ table) [id, transpose, diagonals, antidiagonals]

solve = snd . solve'
solve' [] = (0, 0)
solve' (a:as) =
  let (x, y) = solve' as in
  (max 0 (a + x), max y (a + x))

ans = maximum $ map solve $ list t
