#!/usr/bin/env python3
import os.path

def solve():
	hei = 3
	wid = int(fin.readline())
	s = [fin.readline().strip() for _ in range(hei)]
	a = [1] + [0] * (hei - 1)
	for x in range(wid):
		b = [0] * hei
		for i in range(hei):
			for j in range(hei):
				if abs(i - j) != 1 or s[i][x] != '.' or s[j][x] != '.':
					continue
				b[j] += a[i]
		a = b
	return a[-1] % int(1e9 + 7)


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
