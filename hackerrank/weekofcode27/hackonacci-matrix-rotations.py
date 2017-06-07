#!/usr/bin/env python3

def solve(n, queries):
	a = [[((i + 1) * (j + 1)) ** 2 % 7 in [0, 1, 3, 6] for j in range(n)] for i in range(n)]
	b = a
	ans = []
	for i in range(4):
		diff = 0
		for i in range(n):
			for j in range(n):
				if a[i][j] != b[i][j]:
					diff += 1
		ans.append(diff)
		b = rotate(b)
	return [ans[q // 90 % 4] for q in queries]

def rotate(a):
	return [[a[j][i] for j in range(len(a))] for i in range(len(a[0]) - 1, -1, -1)]

def test():
	assert solve(4, [90, 180, 270]) == [10, 6, 10]
	solve(2000, [10**5] * 10**4)

def read_ints():
	return list(map(int, input().split()))


if __name__ == '__main__':
	#test()
	n, q = read_ints()
	queries = [int(input()) for _ in range(q)]
	print(*solve(n, queries), sep="\n")
