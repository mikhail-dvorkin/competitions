s = map (+ (-2^19)) $ tail $ iterate (\t -> (615949 * t + 797807) `mod` 2^20) 0

t = t' [1..1000] s
t' [] _ = []
t' (a:as) s = (take a s) : (t' as $ drop a s)

q = t' [1..6] [15, -14, -7, 20, -13, -5, -3, 8, 23, -26, 1, -4, -5, -18, 5, -16, 31, 2, 9, 28, 3]

triangles table = foldr process ([], [], 0) table

process row (a, b, c) = if null a then
  (map (:[]) row, take (1 + length row) (cycle [[]]), minimum row)
  else
  let y = zipWith3 (\a -> \b -> \c -> zipWith3 (\a -> \b -> \c -> a + b - c) (tail a) (tail b) c) (a) (tail a) (tail b) in
  let x = zipWith (\a -> \b -> head a + head b) (a) (tail a) in
  let z = zipWith (\a -> \b -> map (+a) b) row $ map (0:) $ zipWith (:) x y in
  (z, a, min c $ minimum $ concat z)

t1 = triangles (drop 900 t)

a = (\(a, b, c) -> c)
d = 10
p = ["t" ++ show n ++ " = foldr process t" ++ show (n + d) ++ " (take " ++ show d ++ " $ drop " ++ show n ++ " t)\n" | n <- [990,980..0]]

-- -56399064 -62432333 -112495847 -150039385

parse :: String -> [Integer]
parse = read

parse' s = map parse $ words s

parse'' :: String -> Integer
parse'' = read

work v u = do
  s <- readFile ("t" ++ show v)
  let ss = lines s
  let tt = (parse' $ ss !! 0, parse' $ ss !! 1, parse'' $ ss !! 2)
  let (a, b, c) = foldr process tt (take u $ drop (v - u) t)
  writeFile ("t" ++ show (v - u)) $ (myshow a) ++ "\n" ++ (myshow b) ++ "\n" ++ (show c)

myshow t = init $ concatMap (\s -> (show s) ++ " ") t
