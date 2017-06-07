#!/usr/bin/env python3
import itertools

def solve(s):
	if not s:
		return 0
	res = 0
	prev = None
	t = []
	a = s[0]
	for c in s:
		if c != prev:
			t.append(c)
		prev = c
		a = min(a, c)
	if t[0] == a:
		return solve(t[1:])
	parts = []
	prev = -1
	res = 0
	for i in range(len(t)):
		if t[i] != a:
			continue
		parts.append(t[prev + 1:i])
		res += 1
		prev = i
	parts[0] = t[prev + 1:] + parts[0]
	bi = None
	for i in range(len(parts)):
		b = parts[i][0]
		z = parts[i - 1][-1]
		c = None
		ii = i
		jj = 0
		while True:
			jj += 1
			if jj == len(parts[ii]):
				ii = (ii + 1) % len(parts)
				jj = 0
			if ii == i and jj == 0:
				break
			if parts[ii][jj] != b:
				c = parts[ii][jj]
				break
		if c is None:
			c = b
		q = (ord(b), 0 if z != b else 1, -ord(c))
		if bi is None or q < bq:
			bq, bi = q, i
	parts = parts[bi:] + parts[:bi]
	t = sum(parts, [])
	return res + solve(t)

def solve_slow(s):
	if not s:
		return 0
	prev = None
	t = []
	m = s[0]
	for c in s:
		if c != prev:
			t.append(c)
		prev = c
		m = min(m, c)
	if t[0] == m:
		return solve_slow(t[1:])
	res = len(t)
	for i in range(len(t)):
		if t[i] != m:
			continue
		res = min(res, solve_slow(t[i + 1:] + t[:i]))
	return 1 + res

def test():
	assert solve('bcabda') == 3
	assert solve('bacabca') == 4
	assert solve('abcdefghij') == 0
	assert solve('acab') == 1
	assert solve('baba') == 2
	assert solve('baca') == 2
	for s in itertools.product("ab", repeat=13):
		assert solve_slow(s) == solve(s), (s, solve_slow(s), solve(s))
	for s in itertools.product("abc", repeat=11):
		assert solve_slow(s) == solve(s), (s, solve_slow(s), solve(s))
	for s in itertools.product("abcd", repeat=6):
		assert solve_slow(s) == solve(s), (s, solve_slow(s), solve(s))

if __name__ == '__main__':
	#test()
	for _ in range(int(input())):
		print(solve(input()))
