module Math where

import List
import Numeric
import Char

digits n = map (toInteger . digitToInt) $ show n

digitsToInt [] = 0;
digitsToInt a = 10 * (digitsToInt (init a)) + last a

digitShift n =
  let d = digits n
  in digitsToInt $ tail d ++ [head d]

digitReverse n = digitsToInt $ reverse $ digits n

digitSum 0 = 0
digitSum n = (n `mod` 10) + digitSum (n `div` 10)

digitNumber 0 = 0
digitNumber n = 1 + digitNumber (n `div` 10)

sameDigits a b = sort (digits a) == sort (digits b)

concatenate a b = a * (10 ^ (digitNumber b)) + b

concatenateList = foldr concatenate 0

palindrome n = (n == digitReverse n)

isPalindrome list = (list == reverse list)

exactlyDigits num =
  let a = 10^(num-1)
      b = 10^num
  in takeWhile (<b) . dropWhile (<a)


pandigital digits = (sort digits) == [1,2,3,4,5,6,7,8,9]

pandigitalInt :: Integer -> Bool
pandigitalInt = pandigital . digits

npandigital n digits = (sort digits) == take (fromInteger n) [1..]

npandigitalInt :: Integer -> Integer -> Bool
npandigitalInt n m = npandigital n (digits m)

lowFactors n = 1 : (filter (\x -> n `mod` x == 0) $ takeWhile (\x -> x * x <= n) [2..])

highFactors n = reverse . map (div n) $ lowFactors n

specialMerge a b =
  if (last a == head b)
    then (a) ++ (tail b)
    else (a) ++ b

factors n = specialMerge (lowFactors n) (highFactors n)

d = sum . init . factors

isAmicable n =
  let m = d n
  in (m /= n && n == d m)

isAbundant n = (d n > n)

abundant = filter isAbundant [1..]

primes :: [Integer]
primes = 2:3:primes'
  where
    1:p:candidates = [6*k+r | k <- [0..], r <- [1,5]]
    primes'        = p : filter isPrime candidates
    isPrime n      = all (not . divides n) $ takeWhile (\p -> p*p <= n) primes'
    divides n p    = n `mod` p == 0

--primes = sieve [ 2.. ]
--         where
--         sieve (p:x) = p : sieve [ n | n <- x, n `mod` p > 0 ]

root n = root' n primes
root' n (p:ps) =
  if (p * p > n) then
    n
  else if (n `mod` (p * p) == 0) then
    root' (n `div` (p * p)) (p : ps)
  else
    root' n ps

composites = myDiff [4..] primes

myDiff list (a:as) = takeWhile (< a) list ++ myDiff (dropWhile (<= a) list) as

pr n = takeWhile (<= n) primes

--isPrime n = (n > 1) && (null . tail . lowFactors $ n)

isPrime n = (n > 1) && isPrime' n primes

isPrime' n (p:primes) =
  if (p * p > n)
    then True
    else if (n `mod` p == 0)
      then False
      else isPrime' n primes

primeFactors n = primeFactorsFrom primes n

primeFactorsFrom _ 1 = []
primeFactorsFrom (p:ps) n =
  if (p * p > n)
    then [n]
    else if (n `mod` p == 0)
      then p : primeFactorsFrom ps (divFully n p)
      else primeFactorsFrom ps n

radical = product . primeFactors

divFully n p =
  if (n `mod` p == 0)
    then divFully (n `div` p) p
    else n

phi n = phi' n (primeFactors n)
phi' n [] = n
phi' n (p:ps) = phi' (n - n `div` p) ps

relPrime a b = gcd a b == 1

listPhi n = foldl (\list -> \p -> filter (\n -> n `mod` p > 0) list) [1..n] (primeFactors n)

listRelPrime n = a
  where a = (listPhi n) ++ map (+ n) a

sigma = sum . factors

extendedGcd a b
  | a < b	= let (g, x, y) = extendedGcd b a in (g, y, x)
  | b == 0	= (a, 1, 0)
  | otherwise	=
    let p = a `div` b in
    let q = a `mod` b in
    let (g, x, y) = extendedGcd b q in
    (g, y, x - p * y)

modInverse p a = let (g, x, y) = extendedGcd a p in x `mod` p

factorials = map factorial [1..]

factorial n = product [1..n]

cnk n k = if (n < 0) || (k < 0) then 0 else (product [(n - k + 1)..n]) `div` (product [1..k])

greater (a, fa) (b, fb) =
  if (fa >= fb)
    then (a, fa)
    else (b, fb)

argmaxPair op (a:[]) = (a, op a)
argmaxPair op (a:as) = greater (a, op a) (argmaxPair op as)

argmax op a = fst $ argmaxPair op a

stairMax f curMax (x:list) =
  let fx = f x
  in if (fx > curMax)
    then (x, fx) : stairMax f fx list
    else stairMax f curMax list
stairMax _ _ [] = []

stairNum = flip zip [1..]

elemSorted value list = value == head (dropWhile (< value) list)

disjointSorted _ [] = True
disjointSorted [] _ = True
disjointSorted (a:as) (b:bs) =
  if (a < b)
    then disjointSorted as (b:bs)
    else if (a > b)
      then disjointSorted (a:as) bs
      else False

mergeSorted a [] = a
mergeSorted [] b = b
mergeSorted (a:as) (b:bs) =
  if (a < b)
    then a : mergeSorted as (b:bs)
    else if (a > b)
      then b : mergeSorted (a:as) bs
      else a : mergeSorted as bs

cumSum = scanl1 (+)

cumOp op initial list = cumOp' op initial list

cumOp' _ _ [] = []
cumOp' op cum (a:as) =
  let c = op cum a
  in c : cumOp' op c as

charact _ [] = []
charact prop (a:as) =
  let rest = charact prop as
  in (if prop a then 1 else 0) : rest

cumProp prop list = cumProp' 0 prop list

cumProp' _ _ [] = []
cumProp' cum prop (a:as) =
  let c = cum + if prop a then 1 else 0
  in c : cumProp' c prop as

getFraction prop list = getFraction' prop list 0 0
getFraction' _ [] _ _ = []
getFraction' prop (l:list) a b =
  let bb = if (prop l) then b + 1 else b in
  (bb, a + 1) : getFraction' prop list (a + 1) bb

ngonal par = cumSum [1,(par-1)..]

mysqrt = mysqrt' 1
mysqrt' m n =
  if (m * m <= n) && ((m + 1) * (m + 1) > n)
    then m
    else mysqrt' ((m + (n `div` m)) `div` 2) n


isSqr n =
  let m = mysqrt n
  in n == m * m
