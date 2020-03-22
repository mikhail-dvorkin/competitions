#!/usr/bin/env python3
import adventofcode
import hashlib
import itertools
import re


def day1(s):
	def balance(t):
		return t.count('(') - t.count(')')

	yield balance(s)
	yield next(i for i in range(len(s) + 1) if balance(s[:i]) < 0)


def day2(s):
	s = s.split()

	def f1(a, b, c):
		return 3 * a * b + 2 * c * (a + b)

	def f2(a, b, c):
		return 2 * (a + b) + a * b * c

	for f in f1, f2:
		yield sum([f(*sorted(map(int, line.split('x')))) for line in s])


def day3(s):
	def visited(t):
		x, y = 0, 0
		v = {(x, y)}
		for c in t:
			dx, dy = adventofcode.DIR[c]
			x += dx
			y += dy
			v.add((x, y))
		return v

	yield len(visited(s))
	yield len(visited(s[::2]).union(visited(s[1::2])))


def day4(s):
	for zeroes in 5, 6:
		yield next(i for i in itertools.count()
			if hashlib.md5(bytes(s + str(i), 'utf8')).hexdigest().startswith('0' * zeroes))


def day5(s):
	s = s.split()
	# noinspection SpellCheckingInspection
	res1 = '([aeiou].*){3}', '(.)\\1', '^(?!.*(ab|cd|pq|xy))'
	res2 = '(..).*\\1', '(.).\\1'
	for res in res1, res2:
		yield sum([all([re.findall(r, line) for r in res]) for line in s])


def day6(s, n=1000):
	functions1 = {'on': lambda t: 1, 'off': lambda t: 0, 'toggle': lambda t: int(not t)}
	functions2 = {'on': lambda t: t + 1, 'off': lambda t: max(t - 1, 0), 'toggle': lambda t: t + 2}
	for functions in functions1, functions2:
		a = [[0] * n for _ in range(n)]
		for line in s.split('\n'):
			*_, action, start, _, end = line.split()
			f = functions[action]
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
	yield s


if __name__ == '__main__':
	adventofcode.run()
