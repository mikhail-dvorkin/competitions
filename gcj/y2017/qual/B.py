#!/usr/bin/python3
import os.path


def solve():
	s = fin.readline().strip()
	n = len(s)
	for m in range(n, -1, -1):
		for d in range(9, -1, -1):
			res = 0
			prev = -1
			ok = True
			for i in range(n):
				if i < m:
					c = int(s[i])
				elif i == m:
					c = d
				else:
					c = 9
				if prev > c:
					ok = False
				prev = c
				res = 10 * res + c
			if ok and res <= int(s):
				return res


filename = os.path.splitext(os.path.basename(__file__))[0].split('_')[0].lower()
fin = open(filename + '.in')
fout = open(filename + '.out', 'w')

tests = int(fin.readline())
for t in range(tests):
	ans = 'Case #{}: {}'.format(t + 1, solve())
	print(ans)
	fout.write(ans + '\n')

fin.close()
fout.close()
