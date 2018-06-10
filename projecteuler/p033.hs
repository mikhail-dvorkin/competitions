module Main where

good a b c d = ((10 * a + b) * c == (10 * c + d) * a && b == d) || ((10 * a + b) * c == (10 * c + d) * b && a == d) || ((10 * a + b) * d == (10 * c + d) * a && b == c) || ((10 * a + b) * d == (10 * c + d) * b && a == c)

fours = [(a,b,c,d) | a <- [1..9], b <- [0..9], c <- [1..9], d <- [0..9], good a b c d]

ans = filter (\(a, b, c, d) -> 10 * a + b < 10 * c + d && b + d > 0) $ fours

-- 16/64, 19/95, 26/65, 49/98

num = 16 * 19 * 26 * 49

den = 64 * 95 * 65 * 98