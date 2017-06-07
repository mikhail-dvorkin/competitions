n, m = map(int, input().split())
a = list(map(int, input().split()))
m -= 1

t = 1
while m:
	if not (t & m):
		t *= 2
		continue
	b = []
	j = t % n
	for i in range(n):
		b.append(a[i] ^ a[j])
		j += 1
		if j == n:
			j = 0
	a = b
	m -= t
	t *= 2
print(*a)
