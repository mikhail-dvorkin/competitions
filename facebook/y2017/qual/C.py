#!/usr/bin/python3
import os.path
import re


def solve():
    h = int(fin.readline().split()[0])
    ans = 0
    for s in fin.readline().split():
    	groups = list(re.match('^(\d+)d(\d+)([+-]\d+)?$', s).groups())
    	if not groups[2]:
    		groups[2] = '0'
    	x, y, z = map(int, groups)
    	z = h - z - x
    	p = [1]
    	for _ in range(x):
    		q = [0] * (len(p) + y - 1)
    		for i in range(len(p)):
    			for j in range(y):
    				q[i + j] += p[i] / y
    		p = q
    	win = sum([p[i] for i in range(len(p)) if i >= z])
    	ans = max(ans, win)
    return ans


filename = os.path.splitext(os.path.basename(__file__))[0].lower()
fin = open(filename + '.in')
fout = open(filename + '.out', 'w')

tests = int(fin.readline())
for t in range(tests):
    ans = 'Case #{}: {}'.format(t + 1, solve())
    print(ans)
    fout.write(ans + '\n')

fin.close()
fout.close()
