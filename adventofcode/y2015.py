#!/usr/bin/env python3
import data
import hashlib
import itertools
import re

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

def day5(s):
	res = '[aeiou]', '(.)\\1', 'ab|cd|pq|xy'
	def good(line):
		a, b, c = [len(re.findall(r, line)) for r in res]
		return a >= 3 and b >= 1 and c == 0
	yield len(list(filter(good, s.split())))

def day6(s, n=1000):
	function = {'on': lambda x: True, 'off': lambda x: False, 'toggle': lambda x: not x}
	a = [[False] * n for _ in range(n)]
	for line in s.split('\n'):
		*_, action, start, _, end = line.split()
		f = function[action]
		x1, y1, x2, y2 = map(int, start.split(',') + end.split(','))
		for x in range(x1, x2 + 1):
			for y in range(y1, y2 + 1):
				a[x][y] = f(a[x][y])
	yield sum(sum(a, []))

def day7(s):
	operators = {'AND': '&', 'OR': '|', 'LSHIFT': '<<', 'RSHIFT': '>>', 'NOT': '~'}
	scope = {"memo": {}}
	for line in s.split('\n'):
		*tokens, _, var = line.split()
		code = '_' + var + '=lambda:(None if "_' + var + '" in memo else memo.update({"_' + var + '": 65535&('
		for token in tokens:
			if token in operators:
				token = operators[token]
			elif not token.isdigit():
				token = '_' + token + '()'
			code += token
		code += ')})) or memo["_' + var + '"]'
		exec(code, scope)
	a = eval('_a()', scope)
	yield a
	scope["memo"] = {}
	scope["_b"] = lambda: a
	yield eval('_a()', scope)

def day8(s):
	yield 0

d = data.y2015
for i in range(len(d) - 1, -1, -1):
	day = 'day' + str(i + 1)
	print(day, *eval(day)(d[i]))
