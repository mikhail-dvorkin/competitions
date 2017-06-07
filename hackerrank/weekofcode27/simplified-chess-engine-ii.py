#!/usr/bin/env python3

N = 4
D = ((1, 0), (0, 1), (-1, 0), (0, -1), (1, 1), (-1, 1), (-1, -1), (1, -1), (2, 1), (1, 2), (-1, 2), (-2, 1), (-2, -1), (-1, -2), (1, -2), (2, -1), (None, -1), (None, 0), (None, 1))
FIGURES = "QNBRP"
RANGE = ((0, 8), (8, 16), (4, 8), (0, 4), (16, 19))


def solve(field, m):
	if (field, m) in memo:
		return memo[(field, m)]
	white = (m % 2 == 1)
	for i in range(N):
		for j in range(N):
			f = field[i][j]
			if f == 0 or ((f > 0) != white):
				continue
			pawn = False
			next = [f]
			for d in range(*RANGE[abs(f) - 1]):
				dx, dy = D[d]
				if dx == None:
					pawn = True
					dx = 1 if white else -1
					must_eat = dy != 0
					if i + 2 * dx in [-1, N]:
						next = [dx * g for g in range(2, abs(f))]
				x, y = i, j
				while True:
					x += dx
					y += dy
					if x < 0 or x >= N or y < 0 or y >= N:
						break
					g = field[x][y]
					if g != 0 and (g > 0) == (f > 0):
						break
					if pawn and ((g != 0) ^ (must_eat)):
						break
					if abs(g) == 1:
						memo[(field, m)] = white
						return white
					if m > 1:
						new = list(map(list, field))
						new[i][j] = 0
						for new[x][y] in next:
							s = solve(tuple(map(tuple, new)), m - 1)
							if white == s:
								memo[(field, m)] = s
								return s
					if g or pawn:
						break
	memo[(field, m)] = not white
	return not white

def read_test():
	w, b, m = map(int, input().split())
	m -= (m + 1) % 2
	field = [[0] * N for _ in range(N)]
	for i in range(w + b):
		figure, col, row = input().split()
		col = ord(col) - ord('A')
		row = int(row) - 1
		figure = FIGURES.find(figure) + 1
		if i >= w:
			figure *= -1
		field[row][col] = figure
	return tuple(map(tuple, field)), m

def test():
	assert solve(((0, 1, 0, 0), (0, 2, 0, 0), (0, 0, 0, 0), (-1, 0, 0, 0)), 1)
	assert solve(((0, 1, 0, 0), (0, 0, 0, 0), (0, 5, 0, 0), (-1, 0, 0, 0)), 1)
	assert solve(((0, 1, 0, 0), (0, 0, 0, 0), (0, 5, 0, 0), (-1, 0, 0, 0)), 5)
	assert solve(((0, 1, 0, 0), (0, -5, -5, 0), (0, 5, 0, 0), (-1, 2, 0, 0)), 1)
	assert solve(((0, 1, 0, 0), (0, -5, -5, 0), (0, 5, 0, 0), (-1, 2, 0, 0)), 3)


memo = {}

if __name__ == '__main__':
	test()
	for _ in range(int(input())):
		print("YES" if solve(*read_test()) else "NO")
