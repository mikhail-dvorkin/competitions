module Sudoku where

import Matching

q = ["100920000",
 "524010000",
 "000000070",
 "050008102",
 "000000000",
 "402700090",
 "060000000",
 "000030945",
 "000071006"]

a = ['1'..'9']
n = 3
coord = take (n ^ 2) [0..]
cells = [(a, b) | a <- coord, b <- coord]

row x s = s !! x
col y s = map (!! y) s

setRow x row s = take x s ++ [row] ++ drop (x + 1) s
setCol y [] [] = []
setCol y (c:col) (r:s) = setRow y c r : setCol y col s

square z s =
  let x = z `div` n in
  let y = z `mod` n in
  concatMap (take n . drop (n * y)) $ take n $ drop (n * x) s

setSquare z square s =
  if (z >= n) then
    take n s ++ setSquare (z - n) square (drop n s)
  else
    zipWith (setSquare' z) (cutBy n square) (take n s) ++ drop n s
setSquare' y sq s = take (n * y) s ++ sq ++ drop (n * y + n) s

cell (x, y) s = (s !! x) !! y
setCell (x, y) cell s = setRow x (setRow y cell (s !! x)) s

cutBy _ [] = []
cutBy n list = take n list : cutBy n (drop n list)

start '0' = a
start d = [d]

valid = null . filter (not . null . filter null)

solved = null . filter (not . null . filter (\s -> length s /= 1))

iter f x =
  let y = f x in
  if (y == x) then
    x
  else
    iter f y

show' list
  | length list > 1	= '?'
  | otherwise		= head list

solveSudoku s = map (map show') $ slv $ map (map start) s

slv s = approachB $ approachA s

approachB s =
  if (solved s) then s else
  let c = head $ dropWhile (\c -> length (cell c s) == 1) cells in
  approachB' s c (cell c s)

approachB' s c [] = [[[]]]
approachB' s c (p : poss) =
  let s' = setCell c [p] s in
  let t = slv s' in
  if (not $ valid t) then
    slv $ setCell c poss s
  else
    t

approachA = iter (doA3 . doA2 . doA1)

doA1 s = foldl doA1' s coord
doA1' s x = setRow x (leavePerfect $ row x s) s

doA2 s = foldl doA2' s coord
doA2' s x = setCol x (leavePerfect $ col x s) s

doA3 s = foldl doA3' s coord
doA3' s x = setSquare x (leavePerfect $ square x s) s
