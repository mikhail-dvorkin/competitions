A = 26
MASKS = (A + 1) * 4
MOD = 10**9 + 7

def solve():
	m = int(input())
	parts = [input() for _ in range(m)]
	s = "".join(parts)
	s = [ord(c) - ord('a') for c in s]
	n = len(s)
	edge = [True] + [False] * n
	i = 0
	for w in parts:
		i += len(w)
		edge[i] = True
	a = [[[0] * MASKS for i in range(n + 1)] for j in range(n + 1)]
	a[0][n][A * 4 + 3] = 1
	for length in range(n, 0, -1):
		for i in range(n + 1 - length):
			j = i + length
			npl = 2 if edge[i + 1] else 0
			npr = 1 if edge[j - 1] else 0
			for mask in range(MASKS):
				value = a[i][j][mask] % MOD
				if not value:
					continue
				letter, pl, pr = mask // 4, mask & 2, mask & 1
				if letter == A:
					a[i + 1][j][s[i] * 4 + npl + pr] += value
					if not edge[i + 1] or not pl:
						a[i + 1][j][A * 4 + (pl | npl) + pr] += value
				else:
					if s[j - 1] == letter:
						a[i][j - 1][A * 4 + pl + npr] += value
					if not edge[j - 1] or not pr:
						a[i][j - 1][letter * 4 + pl + (pr | npr)] += value
	ans = 0
	for i in range(n + 1):
		for mask in range(MASKS):
			letter, pl, pr = mask // 4, mask & 2, mask & 1
			if not edge[i] and pl and pr:
				continue
			ans += a[i][i][mask]
			ans %= MOD
	return ans


for _ in range(int(input())):
	print(solve())
