module Main where

naturals = 1 : [a + 1 | a <- naturals]

dividesBy a b = (b `mod` a == 0)

my_or a b v = (a v) || (b v)

p1 = foldr (+) 0 (filter (my_or (dividesBy 3) (dividesBy 5)) [1..999])

fibs = 1 : 1 : [a + b | (a, b) <- zip fibs (tail fibs)]

lessThan a b = (b < a)

firstThat cond [] = []
firstThat cond (x:xs) =
  if cond x
    then x : firstThat cond xs
    else []

p2 = sum (firstThat (lessThan 4000000) (filter even fibs))

x = 5
y = (6, "Hello")
z = x * fst y

fib 0 = 0
fib 1 = 1
fib n = (fib (n - 1)) + (fib (n - 2))

p16 = sum $ map (\c -> 0 + read [c]) $ show $ 2^1000

p20 = sum $ map (\c -> 0 + read [c]) $ show $ product[1..100]

fac n = product [1..n]

cur = fibs

main = putStrLn(show cur)
