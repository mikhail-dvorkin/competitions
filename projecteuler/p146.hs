import List
import Math

limit = 150000000

s m = filter (t m) [0..(m-1)]
t m n = not $ elem 0 $ u m n
u m n = [(n*n+i) `mod` m | i<-list]
v x = all (\p -> elem (x `mod` p) (s p)) list3

list = [1, 3, 7, 9, 13, 27]
list2 = [2..26] \\ list
list3 = take 7 primes
list4 = primes \\ list3
modulo = product list3
rests = filter v [1..modulo]

good n = good' list (n*n) && good'' list2 (n*n)

good' [] _ = True
good' (p:ps) n = isPrime (n + p) && good' ps n

good'' [] _ = True
good'' (p:ps) n = (not $ isPrime (n + p)) && good'' ps n

numbers = takeWhile (< limit) numbers'
numbers' = rests ++ map (+ modulo) numbers'

badrests p = [p - 1 - ((i - 1) `mod` p) | i <- list]

check p list = (takeWhile (< p) list) ++ (filter (check' p (badrests p)) $ dropWhile (< p) list)
check' p b n =
  let m = n `mod` p in
  let mm = (m * m) `mod` p in
  not $ elem mm b

ans' m n list = foldr check list $ take n $ drop m list4

ans = filter good numbers

a8 = [10,315410,927070,2525870,8146100,16755190,37108670,39313460,97387280,105207350,115458430,119571820,120673970,121288430,122288540,130116970,139519810,139985660,143639500,144774340,147950170]

a = [10,315410,927070,2525870,8146100,16755190,39313460,97387280,119571820,121288430,130116970,139985660]
b = 144774340
