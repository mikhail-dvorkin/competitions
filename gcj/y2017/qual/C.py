#!/usr/bin/python3
import os.path
import collections


def solve():
	n, k = map(int, fin.readline().split())
	a = collections.Counter({n: 1})
	while True:
		space = max(a.keys())
		num = a[space]
		del a[space]
		space -= 1
		parts = space - space // 2, space // 2
		for part in parts:
			a[part] += num
		k -= num
		if k <= 0:
			return parts


filename = os.path.splitext(os.path.basename(__file__))[0].split('_')[0].lower()
fin = open(filename + '.in')
fout = open(filename + '.out', 'w')

tests = int(fin.readline())
for t in range(tests):
	ans = 'Case #{}: {} {}'.format(t + 1, *solve())
	print(ans)
	fout.write(ans + '\n')

fin.close()
fout.close()
