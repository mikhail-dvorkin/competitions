#!/usr/bin/python3
import math
import os.path


def solve():
    n, r = read_ints()
    points = [read_ints() for _ in range(n)]
    ans = 0
    seen = set()
    for c in all_circles(points):
        q = [[], []]
        for p in points:
        	q[1 if in_circle(*p, *c) else 0].append(p)
        mem = tuple(q[0])
        if mem in seen:
        	continue
        seen.add(mem)
        cur = sum([most_populated_square(part, r) for part in q])
        ans = max(ans, cur)
    return ans

def all_circles(p):
	n = len(p)
	res = []
	for i in range(n):
		x1, y1 = p[i]
		res.append((x1, y1, 0))
		for j in range(i):
			x2, y2 = p[j]
			res.append(((x1 + x2) / 2, (y1 + y2) / 2, math.hypot(x2 - x1, y2 - y1) / 2))
			for k in range(j):
				x3, y3 = p[k]
				cc = circumcenter(x1, y1, x2, y2, x3, y3)
				if not cc:
					continue
				x, y = cc
				res.append((x, y, math.hypot(x1 - x, y1 - y)))
	return res

def circumcenter(x1, y1, x2, y2, x3, y3):
	d = x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)
	if not d:
		return None
	x = (x1**2 + y1**2) * (y2 - y3) + (x2**2 + y2**2) * (y3 - y1) + (x3**2 + y3**2) * (y1 - y2)
	y = (x1**2 + y1**2) * (x3 - x2) + (x2**2 + y2**2) * (x1 - x3) + (x3**2 + y3**2) * (x2 - x1)
	return x / d / 2, y / d / 2

def in_circle(x, y, xc, yc, r, eps=1e-9):
	return math.hypot(x - xc, y - yc) <= r + eps

def most_populated_square(p, r):
	res = 0;
	for px in p:
		x1 = px[0]
		for py in p:
			y1 = py[1]
			cur = 0
			for x, y in p:
				if x1 <= x <= x1 + r and y1 <= y <= y1 + r:
					cur += 1
			res = max(res, cur)
	return res

def read_ints():
	return tuple(map(int, fin.readline().split()))


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
