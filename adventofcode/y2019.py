#!/usr/bin/env python3
import adventofcode
import itertools

DIR = {'R': (1, 0), 'U': (0, 1), 'L': (-1, 0), 'D': (0, -1)}

def exec_assembler(s, input=[], output=[]):
	i = 0
	while i < len(s):
		mode, op, jump_to = s[i] // 100, s[i] % 100, []
		i += 1
		if op == 99:
			break
		if op == 1:
			op = lambda x, y: x + y
		elif op == 2:
			op = lambda x, y: x * y
		elif op == 3:
			val, input = input[0], input[1:]
			op = lambda: val
		elif op == 4:
			op = lambda x: output.append(x)
		elif op == 5:
			op = lambda x, y: jump_to.append(y) if x else None
		elif op == 6:
			op = lambda x, y: jump_to.append(y) if not x else None
		elif op == 7:
			op = lambda x, y: 1 if x < y else 0
		elif op == 8:
			op = lambda x, y: 1 if x == y else 0
		args = s[i:i + op.__code__.co_argcount]
		i += len(args)
		for j in range(len(args)):
			if mode % 10 == 0:
				args[j] = s[args[j]]
			mode //= 10
		res = op(*args)
		if res != None:
			s[s[i]] = res
			i += 1
		if jump_to:
			i = jump_to[0]

def day1(s):
	f1 = lambda n: max(n // 3 - 2, 0)
	f2 = lambda n: f1(n) + f2(f1(n)) if n else 0
	for f in f1, f2:
		yield sum([f(int(line)) for line in s.split()])

def day2(s, arg=(12, 2), desired=19690720, maxarg=100):
	program = list(map(int, s.split(',')))
	def run(arg):
		s = program[:]
		s[1:3] = arg
		exec_assembler(s)
		return s[0]
	yield run(arg)
	yield [maxarg * x + y for x in range(maxarg) for y in range(maxarg) if run((x, y)) == desired][0]

def day3(s):
	def layout(s):
		res = {}
		x, y, step = 0, 0, 0
		for token in s.split(','):
			dx, dy = DIR[token[0]]
			for _ in range(int(token[1:])):
				x += dx; y += dy; step += 1
				res[(x, y)] = step
		return res
	sets = [layout(line) for line in s.split()]
	common = set(sets[0]) & set(sets[1])
	yield min([abs(x) + abs(y) for (x, y) in common])
	yield min([sets[0][(x, y)] + sets[1][(x, y)] for (x, y) in common])

def day4(s):
	low, high = map(int, s.split('-'))
	high += 1
	f1 = lambda s: min([ord(y) - ord(x) for x, y in zip(s, s[1:])]) == 0
	f2 = lambda s: f1(s) and 2 in [len(list(group)) for _, group in itertools.groupby(s)]
	for f in f1, f2:
		yield len(list(filter(f, map(str, range(low, high)))))

def day5(s, args=(1, 5)):
	s = list(map(int, s.split(',')))
	for arg in args:
		output = []
		exec_assembler(s[:], [arg], output)
		yield output[-1]

def day6(s, st=('YOU', 'SAN')):
	parent = {}
	for edge in s.split('\n'):
		u, v = edge.split(')')
		parent[v] = u
	def to_root(v):
		return to_root(parent[v]) + [v] if v in parent else []
	yield sum(map(len, map(to_root, parent)))
	a, b = map(set, map(to_root, st))
	yield len(a ^ b) - 2

def day7(s, n=5):
	s = list(map(int, s.split(',')))
	def check(p):
		data = [0]
		for x in p:
			exec_assembler(s[:], [x] + data[-1:], data)
		return data[-1]
	yield max(map(check, itertools.permutations(range(n))))

if __name__ == '__main__':
	adventofcode.run()
