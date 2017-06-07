#!/usr/bin/python3
import os.path


S = 50


def solve():
    n = int(fin.readline())
    w = [int(fin.readline()) for _ in range(n)]
    w.sort(reverse=True)
    need = 0
    for i in range(n):
    	need += (S + w[i] - 1) // w[i]
    	if need > n:
    		return i
    return n


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
