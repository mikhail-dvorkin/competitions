module Main where

import Sudoku

solve [] = []
solve s = (solveSudoku $ take 9 $ drop 1 $ s) : (solve $ drop 10 s)

main = do
  s <- readFile "data\sudoku.txt"
  print $ sum $ map (read . take 3 . head) $ solve $ lines s
