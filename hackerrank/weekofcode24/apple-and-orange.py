data = list(map(int, " ".join([input() for _ in range(5)]).split()))
s, t, a, b, m, n, *data = data
for c, points in (a, data[:m]), (b, data[m:]):
	print(len([p for p in points if s <= c + p <= t]))
