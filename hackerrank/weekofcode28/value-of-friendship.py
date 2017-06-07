#!/usr/bin/env python3
import random

def solve(n, edges):
	dsu = Dsu(n)
	for pair in edges:
		dsu.unite(*pair)
	size = [[0, 0] for _ in range(n)]
	for i in range(n):
		size[dsu.get(i)][0] += 1
	for pair in edges:
		size[dsu.get(pair[0])][1] += 1
	size = [(v, e) for (v, e) in size if v > 0]
	size.sort(reverse=True)
	ans = 0
	sum = 0
	for v, e in size:
		cur = 0
		for i in range(2, v + 1):
			cur = i * (i - 1)
			ans += sum + cur
		sum += cur
	for v, e in size:
		ans += (e - v + 1) * sum
	return ans

class Dsu:
	def __init__(self, n):
		self.r = random.Random(1)
		self.p = [i for i in range(n)]

	def get(self, v):
		if self.p[v] == v:
			return v
		self.p[v] = self.get(self.p[v])
		return self.p[v]

	def unite(self, v, u):
		v, u = self.get(v), self.get(u)
		if self.r.getrandbits(1):
			v, u = u, v
		self.p[v] = u

def test():
	assert solve(5, [(0, 1), (2, 1), (3, 1), (3, 2)]) == 32

def read_test():
	n, m = map(int, input().split())
	edges = [tuple([int(s) - 1 for s in input().split()]) for _ in range(m)]
	return (n, edges)


if __name__ == '__main__':
	#test()
	for _ in range(int(input())):
		print(solve(*read_test()))
