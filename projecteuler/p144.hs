import List
import Math

simplify (a, b) =
  let g = gcd a b in
  (a `div` g, b `div` g)

add (a, b) (c, d) = simplify (a * d + b * c, b * d)

sub (a, b) (c, d) = simplify (a * d - b * c, b * d)

mul (a, b) (c, d) = simplify (a * c, b * d)

frac (a, b) (c, d) =
  if (c == 0) then
    error "Division by zero"
  else
    simplify ((signum c) * a * d, b * (abs c))

mul' n (a, b) = simplify (n * a, b)

isSqr' (a, b) = (isSqr a) && (isSqr b)
sqrt' (a, b) = (mysqrt a, mysqrt b)

abs' (a, b) = (abs a, b)

le (a, b) (c, d) = (a * d <= b * c)

show' (_, ((a, b), (c, d))) = (value (a, b), value (c, d))
value (a, b) = (fromInteger $ (1000000 * a) `div` b) / 1000000

p1 = ((0, 1), (101, 10))
p2 = ((7, 5), (-48, 5))
initRay = (p1, p2)

normal (x, y) = ((x, y), (mul' 5 x, mul' 2 y))

reflect ((x1, y1), (x2, y2)) (x3, y3) =
  let (c, d) = reflect' (sub x2 x1, sub y2 y1) (sub x3 x1, sub y3 y1) in
  (add x1 c, add y1 d)

reflect' (x, y) (a, b) =
  (frac (add (mul' 2 $ mul b $ mul x y) (mul a $ sub (mul x x) (mul y y))) (add (mul x x) (mul y y)),
   frac (add (mul' 2 $ mul a $ mul x y) (mul b $ sub (mul y y) (mul x x))) (add (mul x x) (mul y y)))

intersectEllipse ((x1, y1), (x2, y2)) =
  let a = sub y2 y1 in
  let b = sub x1 x2 in
  let c = sub (mul x2 y1) (mul x1 y2) in
  intersect' a b c

intersect' d e f =
  let a = add (mul' 4 $ mul e e) (mul d d) in
  let b = mul' 8 $ mul e f in
  let c = sub (mul' 4 $ mul f f) (mul' 100 $ mul d d) in
  let ys = solveQuadratic a b c in
  let xs = map (\y -> mul' (-1) $ frac (add f $ mul e y) d) ys in
  zip xs ys

solveQuadratic a b c =
  let d = sub (mul b b) (mul' 4 $ mul a c) in
  if (fst d < 0) then
    []
  else if (fst d == 0) then
    [frac b $ mul' (-2) a]
--  else if (not . isSqr') d then
--    error $ "Irrational root: " ++ (show d)
  else
    [frac (o b $ sqrt' d) (mul' (-2) a) | o <- [sub, add]]

process ((x1, y1), (x2, y2)) =
  let norm = normal (x2, y2) in
  let (x3, y3) = reflect norm (x1, y1) in
  let list = intersectEllipse ((x2, y2), (x3, y3)) in
  let (x4, y4) = exclude list (x2, y2) in
  ((approx x2, approx y2), (approx x4, approx y4))

exclude list point =
  let d0 = dist (list !! 0) point in
  let d1 = dist (list !! 1) point in
  if le d0 d1 then
    list !! 1
  else
    list !! 0
  -- head $ list \\ [point]

dist (x1, y1) (x2, y2) = add (abs' (sub x1 x2)) (abs' (sub y1 y2))

outSide (x, y) = (le (abs' x) (1, 100)) && (fst y > 0)

err = 2^128

approx (a, b) =
  if (b < err) then
    (a, b)
  else
    --error $ (show (a, b)) ++ (show (a `div` er, b `div` er))
    simplify (fromInteger $ (2 * err * a + err) `div` b, 2 * err)

beam = map show' $ takeWhile (not . outSide . snd) $ iterate process initRay