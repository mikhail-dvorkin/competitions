N = 4
D = ((1, 0), (0, 1), (-1, 0), (0, -1), (1, 1), (-1, 1), (-1, -1), (1, -1), (2, 1), (1, 2), (-1, 2), (-2, 1), (-2, -1), (-1, -2), (1, -2), (2, -1))
FIGURES = "QNBR"
RANGE = ((0, 8), (8, 16), (4, 8), (0, 4))


def solve():
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
	return search(tuple(map(tuple, field)), m)

def search(field, m):
	if (field, m) in memo:
		return memo[(field, m)]
	white = (m % 2 == 1)
	for i in range(N):
		for j in range(N):
			f = field[i][j]
			if f == 0 or ((f > 0) != white):
				continue
			for d in range(*RANGE[abs(f) - 1]):
				dx, dy = D[d]
				x, y = i, j
				while True:
					x += dx
					y += dy
					if x < 0 or x >= N or y < 0 or y >= N:
						break
					g = field[x][y]
					if g != 0 and (g > 0) == (f > 0):
						break
					if abs(g) == 1:
						memo[(field, m)] = white
						return white
					if m > 1:
						new = list(map(list, field))
						new[i][j] = 0
						new[x][y] = f
						new = tuple(map(tuple, new))
						s = search(new, m - 1)
						if white == s:
							memo[(field, m)] = s
							return s
					if g:
						break
	memo[(field, m)] = not white
	return not white


memo = {}
for _ in range(int(input())):
	print("YES" if solve() else "NO")
