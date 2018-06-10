module Comb where

subsets 0 _ = [[]]
subsets size set =
  if (size > length set)
    then []
    else (map (head set :) $ subsets (size - 1) (tail set)) ++ subsets size (tail set)

pairs [] = []
pairs (a : as) = (map (\b -> (a, b)) as) ++ (pairs as)

perms [] = [[]]
perms set = perms' [] set

perms' _ [] = []
perms' [] (a:[]) = [[a]]
perms' a (b:c) = (map (b :) $ perms (a ++ c)) ++ perms' (a ++ [b]) c

cycles [] = [[]]
cycles (a:as) = map (a :) $ perms as

subcycles size set = concat [cycles s | s <- subsets size set]

subsets' 0 _ = []
subsets' size set = subsets'' (size - 1) [] set
subsets'' size first [] = []
subsets'' size first (l:last) =
  map (++ [l]) (subsets size first) ++ subsets'' size (first ++ [l]) last

allsubsets [] = [[]]
allsubsets (s:set) = map (s : ) (allsubsets set) ++ allsubsets set

-- TODO it's wrong --
lexCycleFirst list = lexCycleFirst' list (minimum list)
lexCycleFirst' (l:list) m =
  if l == m
    then (l:list)
    else lexCycleFirst' (list ++ [l]) m