import Math

inv n = toRational (1) / toRational (n)
is n = inv n^2

limit = 80
limit2 = 13
desired = inv 2
lst = is limit
g = [n | n <- [2..limit], (last $ primeFactors n) <= limit2]

q = sum . map is

solve s [] list = if (s == desired) then [list] else []
solve s (x:xs) list
  | s > desired		= []
  | s == desired	= [list]
  | s + q (x:xs) < desired = []
  | s + lst > desired	= []
  | otherwise		=
      let ss = s + is x in
      solve ss xs (x:list) ++ solve s xs list

ans = solve 0 g []