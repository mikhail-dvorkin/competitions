#!/usr/bin/python3

def score(s):
	score = 0
	charge = 1
	for c in s:
		if c == 'C':
			charge *= 2
		else:
			score += charge
	return score

def solve():
	d, s = input().split()
	d = int(d)
	swaps = 0
	while score(s) > d:
		if 'CS' not in s:
			return 'IMPOSSIBLE'
		i = s.rindex('CS')
		s = s[:i] + 'SC' + s[i + 2:]
		swaps += 1
	return swaps

tests = int(input())
for t in range(tests):
	print('Case #{}: {}'.format(t + 1, solve()))
