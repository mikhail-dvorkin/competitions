x1, v1, x2, v2 = map(int, input().split())
x = x1 - x2
v = v1 - v2

if v:
	ans = (x % v == 0) and (x // v < 0)
else:
	ans = (x == 0)
print("YES" if ans else "NO")
