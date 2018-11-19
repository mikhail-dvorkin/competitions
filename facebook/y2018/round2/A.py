#!/usr/bin/env python3
import os.path

def solve():
	n, k = map(int, fin.readline().split())
	edges = ['{} {} {}'.format(1, n, k)]
	diff = -k
	length = k - 1
	i = 1
	while i < n:
		i_next = i + 1 if length > 1 else n
		edges.append('{} {} {}'.format(i, i_next, length))
		diff += length
		length -= 1
		i = i_next
	if diff <= 0:
		diff = 0
		edges = edges[:1]
	return '\n'.join([str(diff), str(len(edges))] + edges)


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
