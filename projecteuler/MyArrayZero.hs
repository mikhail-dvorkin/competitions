module MyArrayZero where

data MyArray a = Leaf a | Branch (MyArray a) (MyArray a) Int | Stub Int

makeMyArray length = Stub length
makeBranch my1 my2 = Branch my1 my2 (getLength my1 + getLength my2)

double (Stub len) = Stub (2 * len)
double array = makeBranch array (Stub $ getLength array)

getLength (Leaf _) = 1
getLength (Branch _ _ length) = length
getLength (Stub length) = length

get index array =
  if (index < getLength array) then
    get' index array
  else
    0

get' _ (Leaf x) = x
get' index (Branch my1 my2 len) =
  if (index < getLength my1)
    then get' index my1
    else get' (index - getLength my1) my2
get' _ (Stub _) = 0

inc val index array =
  if (index < getLength array) then
    inc' val index array
  else
    inc val index (double array)

inc' val _ (Leaf x) = Leaf (x + val)
inc' val index (Branch my1 my2 len) =
  if (index < getLength my1)
    then Branch (inc' val index my1) my2 len
    else Branch my1 (inc' val (index - getLength my1) my2) len
inc' val index (Stub 1) = Leaf val
inc' val index (Stub length) =
  let len = length `div` 2 in
  if (index < len)
    then Branch (inc' val index (Stub len)) (Stub (length - len)) length
    else Branch (Stub len) (inc' val (index - len) (Stub (length - len))) length

set val index array =
  if (index < getLength array) then
    set' val index array
  else
    set val index (double array)

set' val _ (Leaf _) = Leaf val
set' val index (Branch my1 my2 len) =
  if (index < getLength my1)
    then Branch (set' val index my1) my2 len
    else Branch my1 (set' val (index - getLength my1) my2) len
set' val index (Stub 1) = Leaf val
set' val index (Stub length) =
  let len = length `div` 2 in
  if (index < len)
    then Branch (set' val index (Stub len)) (Stub (length - len)) length
    else Branch (Stub len) (set' val (index - len) (Stub (length - len))) length

toList (Leaf x) = [x]
toList (Branch my1 my2 _) = toList my1 ++ toList my2
toList (Stub length) = take length [0,0..]

toArray = toArray' 0
toArray' index (Leaf x) = [(index, x)]
toArray' index (Branch my1 my2 len) = (toArray' index my1) ++ (toArray' (index + getLength my1) my2)
toArray' _ (Stub _) = []

--instance Show (MyArray a) where
--  show :: (Show a) => MyArray a -> String
--  show x = show $ toList x
