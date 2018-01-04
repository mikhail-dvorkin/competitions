#!/usr/bin/python3 -B
import data
import hashlib
import itertools

def day1(s):
	yield s.count('(') - s.count(')')

def day2(s):
	sum = 0
	for line in s.split():
		a, b, c = sorted(map(int, line.split('x')))
		sum += 3 * a * b + 2 * c * (a + b)
	yield sum

def day3(s):
	dir = {'^': (0, 1), 'v': (0, -1), '>': (1, 0), '<': (-1, 0)}
	x, y = 0, 0
	visited = {(x, y)}
	for c in s:
		dx, dy = dir[c]
		x += dx
		y += dy
		visited.add((x, y))
	yield len(visited)

def day4(s):
	yield next(i for i in itertools.count() if hashlib.md5(bytes(s + str(i), 'utf8')).hexdigest().startswith('00000'))

d = data.y2015
for i in range(len(d) - 1, -1, -1):
	day = 'day' + str(i + 1)
	print(day, *eval(day)(d[i]))
