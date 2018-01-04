#!/usr/bin/python3 -B
import data
import sys
import re
import functools
import operator
import numpy as np
import collections
import threading

class AttrDict(dict):
    def __init__(self, *args, **kwargs):
        super(AttrDict, self).__init__(*args, **kwargs)
        self.__dict__ = self

def assembler(_prog, _env, _p, _regs=26):
	_env[_p].code = 0
	_env[_p].counter = collections.defaultdict(lambda: 0)
	_oper = dict(set='=', add='+=', sub='-=', mul='*=', mod='%=')
	_jump = dict(jgz='>0', jnz='!=0')
	for _i in range(_regs):
		exec(chr(ord('a') + _i) + '=0')
	while _env[_p].code < len(_prog):
		_action, *_par = _prog[_env[_p].code].split()
		_env[_p].counter[_action] += 1
		if _action in _oper:
			exec(_par[0] + _oper[_action] + _par[1])
		elif _action in _jump:
			if eval(_par[0] + _jump[_action]):
				_env[_p].code += eval(_par[1]) - 1
		elif _action == 'snd':
			_env[_p].output.append(eval(_par[0]))
		elif _action == 'rcv':
			if len(_env) == 1:
				if eval(_par[0]):
					break
			else:
				while not _env[_p ^ 1].output:
					_env[_p].wait = True
					if _env[_p ^ 1].wait and not _env[_p].output and not _env[_p ^ 1].output:
						return
				_env[_p].wait = False
				exec(_par[0] + '=' + str(_env[_p ^ 1].output.popleft()))
		_env[_p].code += 1
	for _i in range(_regs):
		_i = chr(ord('a') + _i)
		_env[_p][_i] = eval(_i)

def exec_assembler(progs):
	env = [AttrDict(output=collections.deque(), wait=False) for p in range(len(progs))]
	threads = [threading.Thread(target=assembler, args=[progs[p], env, p]) for p in range(len(progs))]
	[t.start() for t in threads]
	[t.join() for t in threads]
	return env

def day1(s):
	for shift in 1, len(s) // 2:
		yield sum([int(x) for x, y in zip(s, s[shift:] + s[:shift]) if x == y])

def day2(s):
	s = [list(map(int, line.split())) for line in s.split('\n')]
	f1 = lambda a: max(a) - min(a)
	f2 = lambda a: max([x // y for x in a for y in a if x % y == 0])
	for f in f1, f2:
		yield sum([f(a) for a in s])

def day3(n):
	m = int((n - 1) ** 0.5)
	m -= 1 ^ m & 1
	yield m // 2 + 1 + abs((n - m * m - 1) % (m + 1) - m // 2)
	a = {(0, 0): 1}
	x, y = 1, 0
	dx, dy = 0, 1
	while True:
		while True:
			value = sum([a.get((xn, yn), 0) for xn in range(x - 1, x + 2) for yn in range(y - 1, y + 2)])
			if value != a.get((x - dx, y - dy)):
				break
			x -= dx
			y -= dy
			dx, dy = -dy, dx
			x += dx
			y += dy
		if value > n:
			yield value
			break
		a[(x, y)] = value
		x += dx
		y += dy

def day4(s):
	s = [line.split() for line in s.split('\n')]
	s2 = [list(map(lambda s: ''.join(sorted(s)), array)) for array in s]
	for t in s, s2:
		yield sum([1 for a in t if len(a) == len(set(a))])

def day5(s):
	s = list(map(int, s.split()))
	f1 = lambda x: x + 1
	f2 = lambda x: x - 1 if x >= 3 else x + 1
	for f in f1, f2:
		a = s[:]
		x = 0
		count = 0
		while True:
			count += 1
			x_old = x
			x += a[x]
			a[x_old] = f(a[x_old])
			if x < 0 or x >= len(a):
				break
		yield count

def day6(s):
	s = list(map(int, s.split()))
	n = len(s)
	count = 0
	seen = {tuple(s): 0}
	while True:
		count += 1
		x = s.index(max(s))
		v, s[x] = s[x], 0
		for i in range(n):
			s[(x + 1 + i) % n] += v // n + (1 if i < v % n else 0)
		t = tuple(s)
		if t in seen:
			yield count
			yield count - seen[t]
			break
		seen[t] = count

def day7(s):
	nodes = {}
	for line in s.split('\n'):
		name, weight, kidlist = re.fullmatch(r'(\S+)\s\((\d+)\)(?:\s->\s(.*))?', line).groups()
		kids = kidlist.split(', ') if kidlist else []
		nodes[name] = AttrDict(name=name, kids=kids, weight=int(weight), parent=None)
	for node in nodes.values():
		for kid in node.kids:
			nodes[kid].parent = node.name
	root = [node for node in nodes.values() if not node.parent][0]
	yield root.name
	def dfs(node, ans):
		ws = [dfs(nodes[kid], ans) for kid in node.kids]
		if ws and min(ws) != max(ws) and not ans:
			assert len(ws) > 2
			major = sorted(ws)[len(ws) // 2]
			other = min(ws) ^ max(ws) ^ major
			ans.append(nodes[node.kids[ws.index(other)]].weight - other + major)
		return sum(ws) + node.weight
	ans = []
	dfs(root, ans)
	yield ans[0]

def day8(s):
	_s = s
	del s
	_max = 0
	for _line in _s.split('\n'):
		_line = _line.replace('inc', '+=').replace('dec', '-=') + ' else 0'
		for _var in 4, 0:
			_var = _line.split()[_var]
			if _var not in dir():
				exec(_var + '=0')
		exec(_line)
		_max = max(_max, eval(_var))
	_max_now = 0
	for _var in dir():
		if _var[0] != '_':
			_max_now = max(_max_now, eval(_var))
	yield _max_now
	yield _max

def day9(s):
	level = 0
	garbage = False
	escape = False
	sum_levels = 0
	garbage_len = 0
	for c in s:
		if garbage:
			if escape:
				escape = False
			elif c == '!':
				escape = True
			elif c == '>':
				garbage = False
			else:
				garbage_len += 1
		elif c == '<':
			garbage = True
		elif c == '{':
			level += 1
		elif c == '}':
			sum_levels += level
			level -= 1
	yield sum_levels
	yield garbage_len

def day10(s, n=256, iters=64, salt=[17, 31, 73, 47, 23], hash=False):
	def run(lengths, iters=1):
		a, pos, skip = list(range(n)), 0, 0
		for _ in range(iters):
			for len in lengths:
				a = a[pos:] + a[:pos]
				if len:
					a = a[len - 1::-1] + a[len:]
				a = a[-pos:] + a[:-pos]
				pos = (pos + len + skip) % n
				skip += 1
		return a
	if not hash:
		a = run(map(int, s.split(',')))
		yield a[0] * a[1]
	s = list(map(ord, s)) + salt
	a = run(s, iters)
	m = int(n ** 0.5)
	a = [functools.reduce(operator.__xor__, a[m * i:m * i + m], 0) for i in range(m)]
	yield bytearray(a).hex()

def day11(s):
	moves = {'n': (0, 2), 'ne': (1, 1), 'se': (1, -1), 's': (0, -2), 'sw': (-1, -1), 'nw': (-1, 1)}
	x, y = 0, 0
	max_dist = 0
	for move in s.split(','):
		move = moves[move]
		x += move[0]
		y += move[1]
		dist = abs(x) + max(abs(y) - abs(x), 0) // 2
		max_dist = max(max_dist, dist)
	yield dist
	yield max_dist

def day12(s):
	nei = {}
	for line in s.split('\n'):
		v, u = line.split(' <-> ')
		v = int(v)
		u = list(map(int, u.split(', ')))
		nei[v] = u
	mark = set()
	def dfs(v):
		mark.add(v)
		for u in nei[v]:
			if u not in mark:
				dfs(u)
	dfs(0)
	yield len(mark)
	groups = 1
	for v in nei:
		if v not in mark:
			groups += 1
			dfs(v)
	yield groups

def day13(s):
	severity = 0
	bad = {}
	for line in s.split('\n'):
		depth, force = map(int, line.split(': '))
		mod = 2 * force - 2
		if depth % mod == 0:
			severity += depth * force
		if mod not in bad:
			bad[mod] = set()
		bad[mod].add((-depth) % mod)
	yield severity
	delay = 0
	while True:
		ok = True
		for mod, badset in bad.items():
			if delay % mod in badset:
				ok = False
				break
		if ok:
			break
		delay += 1
	yield delay

def day14(s, n=128):
	f = [bin(int(next(day10(s + '-' + str(i), hash=True)), 16))[2:].zfill(n) for i in range(n)]
	yield ''.join(f).count('1')
	mark = set()
	def dfs(x, y):
		mark.add((x, y))
		for dx, dy in (1, 0), (0, 1), (-1, 0), (0, -1):
			xx, yy = x + dx, y + dy
			if 0 <= xx < n and 0 <= yy < n and f[xx][yy] == '1' and (xx, yy) not in mark:
				dfs(xx, yy)
	groups = 0
	for x in range(n):
		for y in range(n):
			if (x, y) not in mark and f[x][y] == '1':
				groups += 1
				dfs(x, y)
	yield groups

def day15(s):
	def count(x, y, mod1, mod2, n, a=16807, b=48271, mod=2147483647, bits=16):
		same = 0
		for _ in range(int(n)):
			while True:
				x = x * a % mod
				if x % mod1 == 0:
					break
			while True:
				y = y * b % mod
				if y % mod2 == 0:
					break
			if (x ^ y) & ((1 << bits) - 1) == 0:
				same += 1
		return same
	yield count(*s, 1, 1, 4e7)
	yield count(*s, 4, 8, 5e6)

def day16(s, n=16):
	p1, p2 = [list(range(n)) for _ in range(2)]
	for instruction in s.split(','):
		action, value = instruction[0], instruction[1:]
		if action == 's':
			shift = int(value)
			p1 = p1[-shift:] + p1[:-shift]
		elif action == 'x':
			i, j = map(int, value.split('/'))
			p1[i], p1[j] = p1[j], p1[i]
		else:
			i, j = [ord(value[i]) - ord('a') for i in (0, 2)]
			p2[i], p2[j] = p2[j], p2[i]
	m1, m2 = np.zeros((2, n, n), dtype=np.int)
	for i in range(n):
		m1[i][p1[i]] = m2[p2[i]][i] = 1
	for iters in 1, int(1e9):
		p1, p2 = [np.linalg.matrix_power(m, iters).dot(range(n)) for m in (m1, m2)]
		yield ''.join([chr(ord('a') + p2[p1[i]]) for i in range(n)])

def day17(s, n1=2017, n2=int(5e7)):
	a, i = [0], 0
	for v in range(1, n2 + 1):
		i = (i + s) % v + 1
		if v <= n1:
			a.insert(i, v)
			if v == n1:
				yield a[i + 1]
		if i == 1:
			ans = v
	yield ans

def day18(s):
	prog = s.split('\n')
	for mode in 1, 2:
		progs = [['set p ' + str(p)] + prog for p in range(mode)]
		env = exec_assembler(progs)
		if mode == 1:
			yield env[0].output.pop()
		else:
			yield env[1].counter['snd']

def day19(s):
	s = s.split('\n')
	x, y = 0, s[0].index('|')
	dx, dy = 1, 0
	letters, steps = [], 0
	while True:
		steps += 1
		x += dx
		y += dy
		c = s[x][y]
		if c == '+':
			dx, dy = -dy, dx
			way = [s[x + d * dx][y + d * dy] != ' ' for d in (-1, 1)]
			assert way[0] ^ way[1]
			if way[0]:
				dx, dy = -dx, -dy
		elif 'A' <= c <= 'Z':
			letters.append(c)
		elif c == ' ':
			break
	yield ''.join(letters)
	yield steps

def day20(s, dim=3):
	ps = []
	for num, line in enumerate(s.split('\n')):
		line = 'AttrDict(' + line.replace('<', '(').replace('>', ')') + ')'
		p = eval(line)
		p.key = [0, 0, 0]
		p.id = num
		for i in range(dim):
			a, v, x = p.a[i], p.v[i], p.p[i]
			if (a, v, x) < (0, 0, 0):
				a, v, x = -a, -v, -x
			p.key[0] += a
			p.key[1] += v
			p.key[2] += x
		ps.append(p)
	ps.sort(key=lambda p: p.key)
	yield ps[0].id
	collisions = {}
	for p in ps:
		for q in ps:
			if p.id >= q.id:
				continue
			collision = None
			for i in range(dim):
				eq = p.a[i] - q.a[i], 2 * (p.v[i] - q.v[i]) + p.a[i] - q.a[i], 2 * (p.p[i] - q.p[i])
				if eq == (0, 0, 0):
					continue
				roots = [round(x.real) for x in np.roots(eq)]
				roots = set(filter(lambda n: n >= 0 and np.poly1d(eq)(n) == 0, roots))
				if collision == None:
					collision = roots
				else:
					collision.intersection_update(roots)
			if collision:
				collision = min(collision)
				assert collision != 0
				if collision not in collisions:
					collisions[collision] = []
				collisions[collision].append((p.id, q.id))
	alive = set(range(len(ps)))
	for time in sorted(collisions):
		pairs = collisions[time]
		kill = set()
		for p, q in pairs:
			if p in alive and q in alive:
				kill.update({p, q})
		alive.difference_update(kill)
	yield len(alive)

def day21(s, iters=(5, 18), f=('.#.', '..#', '###')):
	def flip(f):
		return tuple(s[::-1] for s in f)
	def rotate(f):
		return tuple(''.join([f[j][i] for j in range(len(f))]) for i in range(len(f) - 1, -1, -1))
	def positions(f):
		for _ in range(2):
			for _ in range(4):
				yield f
				f = rotate(f)
			f = flip(f)
	rules = {}
	for line in s.split('\n'):
		old, new = [tuple(fig.split('/')) for fig in line.split(' => ')]
		assert old not in rules
		for fig in positions(old):
			rules[fig] = new
	for it in range(iters[-1]):
		n = len(f)
		s = 2 + n % 2
		t = s + 1
		m = n // s * t
		g = [[None] * m for _ in range(m)]
		for i in range(0, n, s):
			for j in range(0, n, s):
				old = tuple(f[k][j:j + s] for k in range(i, i + s))
				new = rules[old]
				for x in range(t):
					for y in range(t):
						g[i // s * t + x][j // s * t + y] = new[x][y]
		f = tuple(''.join(s) for s in g)
		if it + 1 in iters:
			yield ''.join(f).count('#')

def day22(s, iters=(10000, int(1e7)), rot=(2, 1)):
	s = s.split('\n')
	for mode in range(len(rot)):
		f = {}
		for i in range(len(s)):
			for j in range(len(s[0])):
				if s[i][j] == '#':
					f[(i - len(s) // 2, j - len(s[0]) // 2)] = 2
		x, y, dx, dy, count = 0, 0, -1, 0, 0
		for _ in range(iters[mode]):
			v = f.get((x, y), 0)
			for _ in range(v + 3):
				dx, dy = dy, -dx
			v = (v + rot[mode]) & 3
			if v == 2:
				count += 1
			f[(x, y)] = v
			x += dx
			y += dy
		yield count

def day23(s):
	prog = s.split('\n')
	env = exec_assembler([prog])[0]
	yield env.counter['mul']
	env = exec_assembler([['set a 1'] + prog[:8]])[0]
	low, high = env.b, env.c
	step = exec_assembler([prog[-2:-1]])[0].b
	h = 0
	for b in range(low, high + step, step):
		for d in range(2, round(b ** 0.5)):
			if b % d == 0:
				h += 1
				break
	yield h

def day24(s):
	nei = collections.defaultdict(lambda: [])
	for line in s.split('\n'):
		a, b = map(int, line.split('/'))
		assert b not in nei[a]
		nei[a].append(b)
		nei[b].append(a)
	def search(opt, x=0, used=set(), accum=0):
		best = opt(accum, used)
		for y in nei[x]:
			e = tuple(sorted([x, y]))
			if e in used:
				continue
			used.add(e)
			best = max(best, search(opt, y, used, accum + x + y))
			used.remove(e)
		return best
	yield search(lambda accum, used: accum)
	yield search(lambda accum, used: (len(used), accum))[1]

def day25(s):
	s = s.split('\n\n')
	rules = {}
	for rule in s[1:]:
		tokens = [line.split()[-1][:-1] for line in rule.split('\n')]
		rules[tokens[0]] = {tokens[1]: tokens[2:5], tokens[5]: tokens[6:9]}
	s = s[0].split()
	state = s[3][0]
	steps = int(s[-2])
	tape, x = set(), 0
	for _ in range(steps):
		value = str(int(x in tape))
		action = rules[state][value]
		if action[0] == '1':
			tape.add(x)
		else:
			tape.remove(x)
		x += -1 if action[1] == 'left' else 1
		state = action[2]
	yield len(tape)

d = data.y2017
for i in range(len(d) - 1, -1, -1):
	day = 'day' + str(i + 1)
	print(day, *eval(day)(d[i]))
