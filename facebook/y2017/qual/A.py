#!/usr/bin/python3
import math
import os.path


R = 100 // 2


def solve():
    p, x, y = map(int, fin.readline().split())
    p /= 100
    x -= R
    y -= R
    if (x, y) == (0, 0):
    	return p > 0
    alpha = math.atan2(x, y) / (2 * math.pi)
    if alpha < 0:
    	alpha += 1
    return math.hypot(x, y) < R and p > alpha


filename = os.path.splitext(os.path.basename(__file__))[0].lower()
fin = open(filename + '.in')
fout = open(filename + '.out', 'w')

tests = int(fin.readline())
for t in range(tests):
    ans = 'black' if solve() else 'white'
    ans = 'Case #{}: {}'.format(t + 1, ans)
    print(ans)
    fout.write(ans + '\n')

fin.close()
fout.close()
