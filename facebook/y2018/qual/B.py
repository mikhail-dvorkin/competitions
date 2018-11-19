#!/usr/bin/env python3
import os.path


def solve():
	n = int(fin.readline())
	p = [int(fin.readline()) for _ in range(n + 1)]
	return "1\n0" if n % 2 else "0"


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
