#!/usr/bin/python3
import os.path


def solve():
	n, m, k = map(int, fin.readline().split())
	if n > m:
		n, m = m, n
	if m < 2 * k + 3 or n <= k:
		return -1
	ans = (n + k - 1) // k
	if n >= 2 * k + 1:
		ans = min(ans, 4 if k >= 2 else 5)
	return ans


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
