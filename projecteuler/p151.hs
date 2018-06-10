import List
import MyArray

hash [] = 0
hash (a:as) = (hash as) * 8 + a

down 1 = []
down n = 1 : map (* 2) (down $ n `div` 2)

zero = -1

solve a [1] = (a, 0)
solve a list =
  let h = hash list in
  let r = get h a in
  if (r /= zero) then
    (a, r)
  else
    let n = length list in
    let (b, s) = add (a, 0) list list in
    let r = (if (n == 1) then 1 else 0) + s / (fromIntegral n) in
    (set r h b, r)

add (a, s) _ [] = (a, s)
add (a, s) list (x:xs) =
  let (b, t) = solve a $ sort $ (list \\ [x]) ++ (down x) in
  add (b, s + t) list xs
  
main = print $ snd $ solve (makeMyArray zero) (down 16)