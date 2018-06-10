module MyArray where

data MyArray a = Leaf a | Branch (MyArray a) (MyArray a) Int | Stub Int

makeMyArray zero = (Leaf zero, zero)

makeBranch' my1 my2 = Branch my1 my2 (getLength' my1 + getLength' my2)

double (array, zero) = (double' array, zero)
double' (Stub len) = Stub (2 * len)
double' array = makeBranch' array (Stub $ getLength' array)

getLength (array, zero) = getLength' array
getLength' (Leaf _) = 1
getLength' (Branch _ _ length) = length
getLength' (Stub length) = length

get index (array, zero) = get' zero index array
get' zero index array =
  if (index < getLength' array) then
    get'' zero index array
  else
    zero

get'' _ _ (Leaf x) = x
get'' zero index (Branch my1 my2 len) =
  if (index < getLength' my1)
    then get'' zero index my1
    else get'' zero (index - getLength' my1) my2
get'' zero _ (Stub _) = zero


change how index (array, zero) = (change' zero how index array, zero)
change' zero how index array =
  if (index < getLength' array) then
    change'' zero how index array
  else
    change' zero how index (double' array)

change'' _ how _ (Leaf x) = Leaf (how x)
change'' zero how index (Branch my1 my2 len) =
  if (index < getLength' my1)
    then Branch (change'' zero how index my1) my2 len
    else Branch my1 (change'' zero how (index - getLength' my1) my2) len
change'' zero how index (Stub 1) = Leaf (how zero)
change'' zero how index (Stub length) =
  let len = length `div` 2 in
  if (index < len)
    then Branch (change'' zero how index (Stub len)) (Stub (length - len)) length
    else Branch (Stub len) (change'' zero how (index - len) (Stub (length - len))) length

st val = change (\x -> val)

set val index (array, zero) = (set' val index array, zero)
set' val index array =
  if (index < getLength' array) then
    set'' val index array
  else
    set' val index (double' array)

set'' val _ (Leaf _) = Leaf val
set'' val index (Branch my1 my2 len) =
  if (index < getLength' my1)
    then Branch (set'' val index my1) my2 len
    else Branch my1 (set'' val (index - getLength' my1) my2) len
set'' val index (Stub 1) = Leaf val
set'' val index (Stub length) =
  let len = length `div` 2 in
  if (index < len)
    then Branch (set'' val index (Stub len)) (Stub (length - len)) length
    else Branch (Stub len) (set'' val (index - len) (Stub (length - len))) length

toList (array, zero) = toList' zero array
toList' _ (Leaf x) = [x]
toList' zero (Branch my1 my2 _) = (toList' zero my1) ++ (toList' zero my2)
toList' zero (Stub length) = take length $ repeat zero

toArray (array, zero) = toArray' array
toArray' = toArray'' 0
toArray'' index (Leaf x) = [(index, x)]
toArray'' index (Branch my1 my2 len) = (toArray'' index my1) ++ (toArray'' (index + getLength' my1) my2)
toArray'' _ (Stub _) = []
