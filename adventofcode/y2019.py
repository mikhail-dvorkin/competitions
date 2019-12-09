#!/usr/bin/env python3
import adventofcode
import collections
import itertools
import threading

def exec_assembler(s, input=[], output=[]):
	if not isinstance(input, collections.deque):
		input = collections.deque(input)
	def read():
		while not input:
			pass
		return input.popleft()
	operations = {
		1: lambda x, y: x + y,
		2: lambda x, y: x * y,
		3: read,
		4: lambda x: output.append(x),
		5: lambda x, y: jump_to.append(y) if x else None,
		6: lambda x, y: jump_to.append(y) if not x else None,
		7: lambda x, y: 1 if x < y else 0,
		8: lambda x, y: 1 if x == y else 0,
		9: lambda x: rel_base_inc.append(x),
		99: lambda: jump_to.append(len(s))
	}
	i, rel_base = 0, 0
	while i < len(s):
		mode, op, jump_to, rel_base_inc = s[i] // 100, operations[s[i] % 100], [], [0]
		i += 1
		args = s[i:i + op.__code__.co_argcount]
		i += len(args)
		for j in range(len(args)):
			if mode % 10 in {0, 2}:
				address = args[j]
				if mode % 10 == 2:
					address += rel_base
				s.extend([0] * max(address + 1 - len(s), 0))
				args[j] = s[address]
			mode //= 10
		res = op(*args)
		if res != None:
			address = s[i]
			if mode == 2:
				address += rel_base
			s.extend([0] * max(address + 1 - len(s), 0))
			s[address] = res
			i += 1
		if jump_to:
			i = jump_to[0]
		rel_base += rel_base_inc[-1]

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
			dx, dy = adventofcode.DIR[token[0]]
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
		pipe = [collections.deque([x]) for x in p]
		pipe[0].append(0)
		threads = [threading.Thread(target=exec_assembler, args=[s[:], pipe[i], pipe[(i + 1) % n]]) for i in range(n)]
		[t.start() for t in threads]
		[t.join() for t in threads]
		return pipe[0][-1]
	for r in range(n), range(n, 2 * n):
		yield max(map(check, itertools.permutations(r)))

def day8(s, w=25, h=6):
	layers = [s[i * w * h:(i + 1) * w * h] for i in range(len(s) // w // h)]
	zeroes = [layer.count('0') for layer in layers]
	layer = layers[zeroes.index(min(zeroes))]
	yield layer.count('1') * layer.count('2')
	image = [[layer[i] for layer in layers if layer[i] < '2'][0] for i in range(w * h)]
	yield '\n'.join([''] + [''.join(image[i * w:(i + 1) * w]).replace('0', ' ') for i in range(h)])

def day9(s):
	yield from day5(s, (1, 2))

if __name__ == '__main__':
	adventofcode.run()
