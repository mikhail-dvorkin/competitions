#!/usr/bin/env python3
import collections
import itertools
import json
import numpy as np
import os
import re
import requests
import sys

class AttrDict(dict):
    def __init__(self, *args, **kwargs):
        super(AttrDict, self).__init__(*args, **kwargs)
        self.__dict__ = self

def day1(s):
	s = list(map(int, s.split()))
	yield sum(s)
	seen = {0}
	for x in itertools.accumulate(itertools.cycle(s)):
		if x in seen:
			yield x
			break
		seen.add(x)

def day2(s):
	s = s.split()
	count_lines = [sum([i in collections.Counter(line).values() for line in s]) for i in (2, 3)]
	yield np.prod(count_lines)
	altered = [line[:i] + '$' + line[i + 1:] for line in s for i in range(len(line))]
	yield next((k for (k, v) in collections.Counter(altered).items() if v > 1)).replace('$', '')

def day3(s):
	s = s.split('\n')
	count = collections.defaultdict(lambda: 0)
	overlap = set()
	uids = set()
	def f1():
		count[(i, j)] += 1
	def f2():
		if count[(i, j)] > 1:
			overlap.add(uid)
	for f in f1, f2:
		for line in s:
			uid, x, y, width, height = map(int, re.fullmatch(r'#(\d+)\s@\s(\d+),(\d+):\s(\d+)x(\d+)', line).groups())
			uids.add(uid)
			for i in range(x, x + width):
				for j in range(y, y + height):
					f()
	yield len([x for x in count.values() if x > 1])
	yield (uids - overlap).pop()

def day4(s):
	s = sorted(s.split('\n'))
	sleep_dict = {}
	for line in s:
		time, event = re.fullmatch(r'\[\d+-\d+-\d+\s\d+:(\d+)\]\s(.+)', line).groups()
		time = int(time)
		if event == 'falls asleep':
			start = time
			continue
		if event != 'wakes up':
			uid = int(re.fullmatch(r'Guard #(\d+) begins shift', event).group(1))
			continue
		sleep_dict.setdefault(uid, [])
		sleep_dict[uid].append((start, time))
		del start
	sleep_count = dict([(k, [0] * 60) for k in sleep_dict])
	for uid, sleeps in sleep_dict.items():
		for start, end in sleeps:
			for i in range(start, end):
				sleep_count[uid][i] += 1
	for f in sum, max:
		selected = max([(uid, f(sleep_count[uid])) for uid in sleep_count], key=lambda item: item[1])[0]
		yield selected * np.argmax(sleep_count[selected])

def day5(s):
	def match(a, b):
		return a.lower() == b.lower() and a != b
	def process(s, forbidden=None):
		stack = []
		for c in s:
			if c.lower() == forbidden:
				continue
			stack.append(c)
			while len(stack) >= 2 and match(*stack[-2:]):
				del stack[-2:]
		return len(stack)
	yield process(s)
	yield min([process(s, chr(ord('a') + i)) for i in range(26)])

def day6(s, limit=10000):
	s = [tuple(map(int, line.split(', '))) for line in s.split('\n')]
	xmin, xmax, ymin, ymax = [f(coords) for coords in zip(*s) for f in [min, max]]
	inf = (xmax - xmin + 1) * (ymax - ymin + 1)
	count = [0] * len(s)
	center = 0
	for x in range(xmin, xmax + 1):
		for y in range(ymin, ymax + 1):
			dist, near, total = inf, None, 0
			for i in range(len(s)):
				cur = abs(s[i][0] - x) + abs(s[i][1] - y)
				total += cur
				if cur < dist:
					dist, near = cur, i
				elif cur == dist:
					near = None
			if near != None:
				count[near] += 1 if xmin < x < xmax and ymin < y < ymax else inf
			if total < limit:
				center += 1
	yield max([x for x in count if x < inf])
	yield center

def day7(s, workers=(1, 5), time_penalty=60):
	s = s.split('\n')
	g = [re.fullmatch(r'Step (\S) must be finished before step (\S) can begin.', line).groups() for line in s]
	for w in workers:
		edges = g[:]
		vertices = set(sum(edges, ()))
		order, free, time, queue = '', w, 0, []
		while queue or vertices:
			while free and vertices:
				possible = vertices - set([edge[1] for edge in edges])
				if not possible:
					break
				v = min(possible)
				queue.append((time + time_penalty + 1 + ord(v) - ord('A'), v))
				free -= 1
				vertices.remove(v)
				order += v
			time, v = min(queue)
			queue.remove((time, v))
			edges = [edge for edge in edges if edge[0] != v]
			free += 1
		yield order if w == 1 else time

def day8(s):
	s = list(map(int, s.split()))
	x = 0
	def parse():
		nonlocal x
		kids_number = s[x]
		x += 1
		entries_number = s[x]
		x += 1
		kids = [parse() for _ in range(kids_number)]
		entries = s[x:x + entries_number]
		x += entries_number
		return (kids, entries)
	root = parse()
	def sum_entries(v):
		return sum(v[1]) + sum([sum_entries(u) for u in v[0]])
	yield sum_entries(root)
	def value(v):
		if not v[0]:
			return sum(v[1])
		return sum([value(v[0][index - 1]) for index in v[1] if 0 <= index - 1 < len(v[0])])
	yield value(root)

def day9(s, coef=100, period=23, stepback=7):
	p, n = map(int, re.fullmatch(r'(\d+) players; last marble is worth (\d+) points', s).groups())
	a = collections.deque([0])
	score = [0] * p
	for i in range(1, coef * n + 1):
		if i % period:
			a.extend([a.popleft(), i])
		else:
			for _ in range(stepback):
				a.appendleft(a.pop())
			score[(i - 1) % p] += i + a.pop()
			a.append(a.popleft())
		if i in [n, coef * n]:
			yield max(score)

def day10(s):
	s = s.split('\n')
	points = [tuple(map(int, re.fullmatch(r'position=<\s*(\S+),\s*(\S+)> velocity=<\s*(\S+),\s*(\S+)>', line).groups())) for line in s]
	xa, ya, vxa, vya = tuple(map(np.average, zip(*points)))
	sa = sum([(x - xa) * (vx - vxa) + (y - ya) * (vy - vya) for x, y, vx, vy in points])
	sb = sum([(vx - vxa) ** 2 + (vy - vya) ** 2 for x, y, vx, vy in points])
	t = int(round(-sa / sb))
	pixels = [(x + t * vx, y + t * vy) for x, y, vx, vy in points]
	minx, maxx, miny, maxy = [f(zs) for zs in zip(*pixels) for f in [min, max]]
	a = [['.'] * (maxx + 1 - minx) for _ in range(maxy + 1 - miny)]
	for x, y in pixels:
		a[y - miny][x - minx] = '#'
	yield '\n'.join([''] + [''.join(line) for line in a])
	yield t

def day11(n, m=300, simple=[3]):
	def f(x, y):
		r = x + 10
		r = (r * y + n) * r
		return r % 1000 // 100 - 5
	a = [[f(x, y) for y in range(m)] for x in range(m)]
	p = [[0 for y in range(m + 1)] for x in range(m + 1)]
	for x in range(m):
		for y in range(m):
			p[x + 1][y + 1] = p[x + 1][y] + p[x][y + 1] - p[x][y] + a[x][y]
	for sizes in [simple, range(1, m + 1)]:
		best = (float("-inf"),)
		for s in sizes:
			for x in range(m - s + 1):
				for y in range(m - s + 1):
					cur = p[x + s][y + s] - p[x][y + s] - p[x + s][y] + p[x][y]
					best = max(best, (cur, x, y, s))
		best = best[1:3] if sizes == simple else best[1:]
		yield ','.join(map(str, best))

def day12(s, simple=20, hard=50000000000, stable=128):
	f, _, *s = s.split('\n')
	f = f.split()[-1]
	rules = dict([line.split(' => ') for line in s])
	nei = len(next(iter(rules)))
	ans = []
	for gen in range(2 * stable):
		ans.append(sum([i - (nei // 2) * gen for i in range(len(f)) if f[i] == '#']))
		f = '.' * (nei - 1) + f + '.' * (nei - 1)
		f = ''.join([rules[f[i:i + nei]] for i in range(len(f) + 1 - nei)])
	yield ans[simple]
	xs, ys = range(stable, 2 * stable), ans[-stable:]
	yield int(round(np.poly1d(np.polyfit(xs, ys, 1))(hard)))

def day13(s):
	s = s.split('\n')
	carts = []
	for y in range(len(s)):
		s[y] = list(s[y])
		for x in range(len(s[y])):
			for char, dy, dx in [('^', -1, 0), ('v', 1, 0), ('<', 0, -1), ('>', 0, 1)]:
				if s[y][x] == char:
					s[y][x] = '-' if dy == 0 else '|'
					cart = AttrDict(y=y, x=x, dy=dy, dx=dx, c=0, alive=True)
					carts.append(cart)
	occupied = dict([((cart.y, cart.x), cart) for cart in carts])
	firstcrash = None
	while len(carts) > 1:
		carts.sort(key=lambda cart: (cart.y, cart.x))
		for cart in carts:
			if not cart.alive:
				continue
			del occupied[(cart.y, cart.x)]
			cart.y += cart.dy
			cart.x += cart.dx
			if (cart.y, cart.x) in occupied:
				firstcrash = firstcrash or cart
				that = occupied[(cart.y, cart.x)]
				cart.alive = that.alive = False
				del occupied[(cart.y, cart.x)]
				continue
			occupied[(cart.y, cart.x)] = cart
			if s[cart.y][cart.x] == '/':
				cart.dy, cart.dx = -cart.dx, -cart.dy
			elif s[cart.y][cart.x] == '\\':
				cart.dy, cart.dx = cart.dx, cart.dy
			elif s[cart.y][cart.x] == '+':
				if cart.c == 0:
					cart.dy, cart.dx = -cart.dx, cart.dy
				elif cart.c == 2:
					cart.dy, cart.dx = cart.dx, -cart.dy
				cart.c = (cart.c + 1) % 3
		carts = [cart for cart in carts if cart.alive]
	for cart in firstcrash, carts[0]:
		yield "{},{}".format(cart.x, cart.y)

def day14(n, a=[3, 7], pos=[0, 1], window=10):
	searched = list(map(int, str(n)))
	ans = [None] * 2
	while not all(ans):
		s = str(a[pos[0]] + a[pos[1]])
		for d in map(int, s):
			a.append(d)
			if a[-len(searched):] == searched:
				ans[1] = ans[1] or len(a) - len(searched)
		pos = [(x + 1 + a[x]) % len(a) for x in pos]
		if len(a) >= n + window:
			ans[0] = ans[0] or ''.join(map(str, a[n:n + window]))
	yield from ans

def day15(s, enemies='EG', empty='.', attack=3, health=200):
	D = [(-1, 0), (0, -1), (0, 1), (1, 0)]
	s = list(map(list, s.split('\n')))
	hei, wid = len(s), len(s[0])
	inf = max(health, hei * wid) + 1
	f = None

	def outside(y, x):
		return y < 0 or y >= hei or x < 0 or x >= wid

	def not_empty(y, x):
		return outside(y, x) or f[y][x] != empty

	def bfs(y, x):
		dist = [[inf] * wid for _ in range(hei)]
		dist[y][x] = 0
		queue = [(y, x)]
		index = 0
		while index < len(queue):
			y, x = queue[index]
			index += 1
			for dy, dx in D:
				yy, xx = y + dy, x + dx
				if not_empty(yy, xx) or dist[yy][xx] != inf:
					continue
				dist[yy][xx] = dist[y][x] + 1
				queue.append((yy, xx))
		return dist

	def run(attack_high=attack):
		nonlocal f
		f = [s[i][:] for i in range(hei)]
		hp = [[(health if c in enemies else 0) for c in f[i]] for i in range(hei)]
		weak = False
		for time in itertools.count():
			order = []
			dead = set()
			for y in range(hei):
				for x in range(wid):
					if f[y][x] in enemies:
						order.append((y, x))
			for yf, xf in order:
				if f[yf][xf] not in enemies or (yf, xf) in dead:
					continue
				dist = bfs(yf, xf)
				target = (inf,)
				gameover = True
				for yt in range(hei):
					for xt in range(wid):
						if f[yt][xt] not in enemies or f[yt][xt] == f[yf][xf]:
							continue
						gameover = False
						for dy, dx in D:
							yn, xn = yt + dy, xt + dx
							if (yn, xn) == (yf, xf):
								target = (-1,)
							if not_empty(yn, xn):
								continue
							target = min(target, (dist[yn][xn], yn, xn))
				if gameover:
					break
				if target[0] not in [-1, inf]:
					dist = bfs(*target[1:])
					move = (inf,)
					for dy, dx in D:
						yn, xn = yf + dy, xf + dx
						if not_empty(yn, xn):
							continue
						move = min(move, (dist[yn][xn], yn, xn))
					yn, xn = move[1:]
					f[yn][xn], hp[yn][xn] = f[yf][xf], hp[yf][xf]
					f[yf][xf], hp[yf][xf] = empty, 0
					yf, xf = yn, xn
				target = (inf,)
				for dy, dx in D:
					yn, xn = yf + dy, xf + dx
					if outside(yn, xn) or f[yn][xn] not in enemies or f[yn][xn] == f[yf][xf]:
						continue
					target = min(target, (hp[yn][xn], yn, xn))
				if target[0] == inf:
					continue
				yt, xt = target[1:]
				hp[yt][xt] -= attack_high if f[yf][xf] == enemies[0] else attack
				if hp[yt][xt] <= 0:
					if f[yt][xt] == enemies[0]:
						weak = True
					f[yt][xt], hp[yt][xt] = empty, 0
					dead.add((yt, xt))
			if gameover:
				break
		return time * sum(sum(hp, [])), weak
	yield run()[0]
	for i in itertools.count(attack):
		outcome, weak = run(i)
		if not weak:
			yield outcome
			return

def day16(s, ambig_threshold=3):
	commands = {
		'addr': lambda a, b: r[a] + r[b],
		'addi': lambda a, b: r[a] + b,
		'mulr': lambda a, b: r[a] * r[b],
		'muli': lambda a, b: r[a] * b,
		'banr': lambda a, b: r[a] & r[b],
		'bani': lambda a, b: r[a] & b,
		'borr': lambda a, b: r[a] | r[b],
		'bori': lambda a, b: r[a] | b,
		'setr': lambda a, b: r[a],
		'seti': lambda a, b: a,
		'gtir': lambda a, b: int(a > r[b]),
		'gtri': lambda a, b: int(r[a] > b),
		'gtrr': lambda a, b: int(r[a] > r[b]),
		'eqir': lambda a, b: int(a == r[b]),
		'eqri': lambda a, b: int(r[a] == b),
		'eqrr': lambda a, b: int(r[a] == r[b])
	}
	def apply(command, a, b, c):
		r[c] = commands[command](a, b)
	samples, program = s.split('\n' * 4)
	samples = samples.split('\n' * 2)
	ambig = 0
	possible = [set(commands) for _ in range(len(commands))]
	for sample in samples:
		before, code, after = [list(map(int, re.findall(r'\d+', s))) for s in sample.split('\n')]
		good = 0
		for command in commands:
			r = before[:]
			apply(command, *code[1:])
			if r == after:
				good += 1
			else:
				possible[code[0]].discard(command)
		if good >= ambig_threshold:
			ambig += 1
	yield ambig
	for _ in range(len(commands)):
		for i in range(len(commands)):
			if len(possible[i]) > 1:
				continue
			know = next(iter(possible[i]))
			for j in range(len(commands)):
				if i == j:
					continue
				possible[j].discard(know)
	program = program.split('\n')
	r = [0] * len(r)
	for code in program:
		code = list(map(int, code.split()))
		apply(next(iter(possible[code[0]])), *code[1:])
	yield r[0]

if __name__ == '__main__':
	sys.setrecursionlimit(max(10 ** 6, sys.getrecursionlimit()))
	year = "2018"
	filename = "data~.json"
	if os.path.isfile(filename):
		with open("data~.json", "r") as read_file: d = json.load(read_file)
	else:
		d = requests.get('https://pastebin.com/raw/xGvU9SZY').json()
	d = d[year]
	for i in range(len(d) - 1, -1, -1):
		day = 'day' + str(i + 1)
		print(day, *eval(day)(d[i]))
