module Main where

import List

{-
GO   0
A1   1
CC1  2
A2   3
T1   4
R1   5
B1   6
CH1  7
B2   8
B3   9
JAIL 10
C1   11
U1   12
C2   13
C3   14
R2   15
D1   16
CC2  17
D2   18
D3   19
FP   20
E1   21
CH2  22
E2   23
E3   24
R3   25
F1   26
F2   27
U2   28
F3   29
G2J  30
G1   31
G2   32
CC3  33
G3   34
R4   35
CH3  36
H1   37
T2   38
H2   39
-}

s = 4

pseudo :: [Integer]
pseudo = 13 : map (\n -> n * n `mod` 198802193) pseudo

way = way' 0 (map fromInteger pseudo) 0
way' cur (aa:bb:c:dice) conseq =
  let a = aa `mod` s + 1 in
  let b = bb `mod` s + 1 in
  if (a == b)
    then way'' cur (a:b:c:dice) (conseq + 1)
    else way'' cur (a:b:c:dice) 0
way'' cur (a:b:c:dice) 3 = way' 10 dice 0
way'' cur (a:b:c:dice) conseq =
  cur : way' (move cur (a + b) c) dice conseq

move cur s rnd = advance (rnd `mod` 16) ((rnd `div` 16) `mod` 16) $ (cur + s) `mod` 40

advance rnd rnd2 c =
  if (c == 30)
    then 10
    else if (c == 2 || c == 17 || c == 33)
      then advanceCC rnd c
      else if (c == 7 || c == 22 || c == 36)
        then advanceCH rnd rnd2 c
        else c

advanceCC 1 _ = 0
advanceCC 2 _ = 10
advanceCC _ c = c

advanceCH 1 _ _ = 0
advanceCH 2 _ _ = 10
advanceCH 3 _ _ = 11
advanceCH 4 _ _ = 24
advanceCH 5 _ _ = 39
advanceCH 6 _ _ = 5
advanceCH 7 _ c = nextR c
advanceCH 8 _ c = nextR c
advanceCH 9 _ c = nextU c
advanceCH 10 rnd2 c = advance rnd2 0 ((c + 37) `mod` 40)
advanceCH _ _ c = c

nextR 7 = 15
nextR 22 = 25
nextR 36 = 5
nextU 7 = 12
nextU 22 = 28
nextU 36 = 12

stat list = reverse $ sort $ zip (stat'' list) [0..]
stat'' list = stat' list $ take 40 $ repeat 0
stat' [] st = st
stat' (l:list) st = stat' list $ inc l st

inc n list = take n list ++ [(list !! n) + 1] ++ drop (n + 1) list

main = print $ stat $ take 100000 way
