#!/usr/bin/env python3
import collections
import itertools
import json
import numpy as np
import re
import requests

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
	f, s = s.split('\n\n')
	f = f.split()[-1]
	rules = {}
	for line in s.split('\n'):
		local, result = line.split(' => ')
		rules[local] = result
	x = 0
	ans = [None]
	for gen in range(2 * stable - 1):
		f = '....' + f + '....'
		r = ''
		for i in range(len(f) + 1 - 5):
			r += rules[f[i:i + 5]]
		x += 2
		f = r
		ans.append(sum([i - x for i in range(len(f)) if f[i] == '#']))
	yield ans[simple]
	x = np.arange(stable, 2 * stable)
	y = np.array(ans[-stable:])
	k, b = [int(round(v)) for v in np.polyfit(x, y, 1)]
	yield k * hard + b

if __name__ == '__main__':
	year = "2018"
	d = requests.get('https://pastebin.com/raw/xGvU9SZY').json()[year]
	#with open("data~.json", "r") as read_file: d = json.load(read_file)[year]
	for i in range(len(d) - 1, -1, -1):
		day = 'day' + str(i + 1)
		print(day, *eval(day)(d[i]))
