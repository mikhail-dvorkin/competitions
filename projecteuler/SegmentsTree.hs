module SegmentsTree where

data ST = Leaf Int | Branch ST ST Int Int

getMax (Leaf x) = x
getMax (Branch _ _ _ x) = x

getLength (Leaf x) = 1
getLength (Branch _ _ l _) = l

makeST [] = error "Can't make an empty segments tree"
makeST (x:[]) = Leaf x
makeST list =
  let l = (length list) `div` 2
  in join (makeST $ take l list) (makeST $ drop l list)

join st1 st2 = Branch st1 st2 (getLength st1 + getLength st2) (max (getMax st1) (getMax st2))

toList (Leaf x) = [x]
toList (Branch st1 st2 _ _) = toList st1 ++ toList st2

showST (Leaf x) = show x
showST (Branch _ _ _ x) = show x

instance Show ST where
  show x = show $ toList x

get 0 (Leaf x) = x
--get _ (Leaf x) = error "index out of bounds"
get index (Branch st1 st2 len x) =
  if (index < getLength st1)
    then get index st1
    else get (index - getLength st1) st2

set 0 value (Leaf x) = Leaf value
set _ _ (Leaf x) = error "index out of bounds"
set index value (Branch st1 st2 len x) =
  if (index < getLength st1)
    then join (set index value st1) st2
    else join st1 (set (index - getLength st1) value st2)

add index delta st = set index (delta + get index st) st

leftmostAtLeast bound (Leaf x) = if (x < bound) then -1 else 0
leftmostAtLeast bound (Branch st1 st2 len max) =
  if (max < bound) then -1 else
  if (getMax st1 >= bound)
    then leftmostAtLeast bound st1
    else getLength st1 + leftmostAtLeast bound st2