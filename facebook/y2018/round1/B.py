#!/usr/bin/python3
import os.path
import sys
sys.setrecursionlimit(int(1e6))

def solve():
	n, k = map(int, fin.readline().split())
	tree = [list(map(lambda s: int(s) - 1, fin.readline().split())) for _ in range(n)]
	p, q = [order(0, tree, pre) for pre in [False, True]]
	label = [-1] * n
	for i in range(n):
		if label[i] >= 0:
			continue;
		j = i
		while True:
			label[j] = max(k, 1)
			j = q[p.index(j)]
			if j == i:
				break
		k -= 1
	return ' '.join(map(str, label)) if k <= 0 else "Impossible"

def order(v, tree, pre):
	if v == -1:
		return []
	res = sum([order(u, tree, pre) for u in tree[v]], [])
	return [v] + res if pre else res + [v]


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
