module Poker where

import Char
import List
import Misc

value 'T' = 10
value 'J' = 11
value 'Q' = 12
value 'K' = 13
value 'A' = 14
value digit = Char.digitToInt digit

parse card = (value $ head card, last card)

flush hand = (length $ collect $ map snd hand) == 1

straight' (a:[]) = True
straight' (a:b:list) = (a + 1 == b) && straight' (b:list)

straight = straight' . values

values = sort . (map fst)

amounts = reverse . sort . (map snd) . collect . values

those amount hand = reverse [a | (a, b) <- collect $ values hand, b == amount]

low = head . values

high = last . values

force hand | (straight hand && flush hand && high hand == 14) = [10]
           | (straight hand && flush hand) = [9, high hand]
           | ([4, 1] == amounts hand) = [8] ++ (those 4 hand) ++ (those 1 hand)
           | ([3, 2] == amounts hand) = [7] ++ (those 3 hand) ++ (those 2 hand)
           | (flush hand) = [6] ++ (those 1 hand)
           | (straight hand) = [5, high hand]
           | ([3, 1, 1] == amounts hand) = [4] ++ (those 3 hand) ++ (those 1 hand)
           | ([2, 2, 1] == amounts hand) = [3] ++ (those 2 hand) ++ (those 1 hand)
           | ([2, 1, 1, 1] == amounts hand) = [2] ++ (those 2 hand) ++ (those 1 hand)
           | ([1, 1, 1, 1, 1] == amounts hand) = [1] ++ (those 1 hand)
           | 1 > 0 = error $ "invalid hand " ++ show hand

winner tenCards =
  let twoHands = map parse tenCards
  in force (take 5 twoHands) > force (drop 5 twoHands)
