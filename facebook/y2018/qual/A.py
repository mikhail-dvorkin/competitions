#!/usr/bin/python3
import os.path


def solve():
	n, k, visit = map(int, fin.readline().split())
	names = [fin.readline().strip() for _ in range(n)]
	start = (visit - 1) * k % n
	indices = [x % n for x in range(start, start + k)]
	return ' '.join([names[i] for i in sorted(indices)])


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
