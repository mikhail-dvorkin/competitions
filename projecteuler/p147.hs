x = 47
y = 43

s a b
  | a > x	= 0
  | b > y	= 0
  | otherwise	= (x - a + 1) * (x - a + 2) * (y - b + 1) * (y - b + 2) `div` 4

up n = (n + 1) `div` 2

down n = n `div` 2

p a b = s (up a + up b) (up $ a + b)

q a b = s (1 + down a + down b) (1 + (down $ a + b))

m = 2 * x + 2 * y

solve = sum [s a b + p a b + q a b | a <- [1..m], b <- [1..m]]