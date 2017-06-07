def coord(x, y, a, b):
	return (x * a + y * b) / (a ** 2 + b ** 2)


x, y, a, b = map(int, (input() + ' ' + input()).split())
print(coord(x, y, a, b))
print(coord(x, y, -b, a))
