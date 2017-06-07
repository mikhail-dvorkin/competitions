n = int(input())
f = [[c == '.' for c in input()] for _ in range(n)]

r2 = 0
for xc in range(n):
	for yc in range(n):
		if not f[xc][yc]:
			continue
		closest = 2 * n ** 2
		for x in range(-1, n + 1):
			for y in range(-1, n + 1):
				if 0 <= x < n and 0 <= y < n and f[x][y]:
					continue
				closest = min(closest, (x - xc) ** 2 + (y - yc) ** 2)
		r2 = max(r2, closest)

r = 0
while (r + 1) ** 2 < r2:
	r += 1
print(r)
