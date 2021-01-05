#!/usr/bin/env python3
import adventofcode
import functools
import re
import scipy.optimize


def day1(s, n=2020):
	s = set(map(int, s.split('\n')))
	def find(collection, desired_sum):
		return [x for x in collection if desired_sum - x in collection]
	for ans in find(s, n), [x for x in s if find(s - {x}, n - x)]:
		yield functools.reduce(int.__mul__, ans)

def day2(s):
	good1, good2 = 0, 0
	for line in s.split('\n'):
		range_str, letter_str, password = line.split()
		low, high = map(int, range_str.split("-"))
		letter = letter_str[0]
		if low <= password.count(letter) <= high:
			good1 += 1
		if (password[low - 1] + password[high - 1]).count(letter) == 1:
			good2 += 1
	yield good1
	yield good2

def day3(s, dirs=(((3, 1),), ((1, 1), (3, 1), (5, 1), (7, 1), (1, 2)))):
	s = s.split('\n')
	def count(dx, dy):
		return len([1 for i in range(len(s)) if i % dy == 0 and s[i][i * dx // dy % len(s[i])] == '#'])
	for dir_list in dirs:
		yield functools.reduce(int.__mul__, [count(dx, dy) for dx, dy in dir_list])

def day4(s):
	checks = {
		'byr': r'19[2-9]\d|200[0-2]',
		'iyr': r'201\d|2020',
		'eyr': r'202\d|2030',
		'hgt': r'(1[5-8]\d|19[0-3])cm|(59|6\d|7[0-6])in',
		'hcl': r'#[0-9a-f]{6}',
		'ecl': r'amb|blu|brn|gry|grn|hzl|oth',
		'pid': r'\d{9}',
	}
	ans1, ans2 = 0, 0
	for entry in s.split('\n\n'):
		passport = dict([t.split(':') for t in entry.split()])
		if set(passport).issuperset(checks.keys()):
			ans1 += 1
			ans2 += all([re.fullmatch(regex, passport[key]) for key, regex in checks.items()])
	yield ans1
	yield ans2

def day5(s, trans=("FB" + "LR", "0101")):
	s = [int(x.translate(str.maketrans(*trans)), 2) for x in s.split('\n')]
	yield max(s)
	yield min(set(range(min(s), max(s) + 1)) - set(s))

def day6(s):
	s = [[set(line) for line in group.split('\n')] for group in s.split('\n\n')]
	for op in set.union, set.intersection:
		yield sum([len(functools.reduce(op, group)) for group in s])

def day7(s, my='shiny gold'):
	dag = {}
	for line in s.split('\n'):
		a, b = line.split(' bags contain ')
		dag[a] = {}
		for term in b.split(','):
			c, d, e, *_ = term.split()
			if c != 'no':
				dag[a][d + " " + e] = int(c)
	def good(v):
		return True if v == my else any([good(u) for u in dag[v]])
	yield len([v for v in dag if good(v)]) - 1
	def size(v):
		return 1 + sum([dag[v][u] * size(u) for u in dag[v]])
	yield size(my) - 1

def day8(s):
	s = s.split('\n')
	def exec_assembler(changed=-1):
		i = 0
		acc = 0
		executed = set()
		while i not in executed and i < len(s):
			op, arg = s[i].split()
			if i == changed:
				if op == 'nop':
					op = 'jmp'
				elif op == 'jmp':
					op = 'nop'
			arg = int(arg)
			executed.add(i)
			i += 1
			if op == 'acc':
				acc += arg
			elif op == 'jmp':
				i += arg - 1
		return i == len(s), acc
	yield exec_assembler()[1]
	yield max([exec_assembler(changed) for changed in range(len(s))])[1]

def day9(s, m=25):
	s, i = [int(x) for x in s.split('\n')], None
	for i in range(m, len(s)):
		prev = set(s[i - m:i])
		if not any([s[i] - x in prev for x in prev]):
			break
	j, k, diff = 0, 0, s[i]
	yield diff
	while diff:
		if diff > 0:
			diff -= s[k]
			k += 1
		else:
			diff += s[j]
			j += 1
	yield min(s[j:k]) + max(s[j:k])

def day10(s):
	s = sorted([int(x) for x in s.split('\n')])
	s = [0] + s + [max(s) + 3]
	diffs = [b - a for a, b in zip(s, s[1:])]
	yield diffs.count(1) * diffs.count(3)
	a = [1]
	for i in range(s[-1]):
		a.append(sum(a[-3:]) * (i + 1 in s))
	yield a[-1]

def day11(s, parameters=((4, False), (5, True))):
	s = s.split('\n')
	hei, wid = len(s), len(s[0])
	def model(field, threshold, long):
		field_next = [list(row) for row in field]
		for i in range(hei):
			for j in range(wid):
				nei = 0
				for di in range(-1, 2):
					for dj in range(-1, 2):
						if di == dj == 0:
							continue
						ii, jj = i, j
						while True:
							ii, jj = ii + di, jj + dj
							if not (long and 0 <= ii < hei and 0 <= jj < wid and field[ii][jj] == '.'):
								break
						if 0 <= ii < hei and 0 <= jj < wid and field[ii][jj] == '#':
							nei += 1
				if field[i][j] == 'L' and nei == 0:
					field_next[i][j] = '#'
				if field[i][j] == '#' and nei >= threshold:
					field_next[i][j] = 'L'
		if field_next == field:
			return sum(field, []).count('#')
		return model(field_next, threshold, long)
	for p in parameters:
		yield model(s, *p)

def day12(s):
	for mode in range(2):
		x, y = 0, 0
		dx, dy = (10, 1) if mode else adventofcode.DIR['E']
		for line in s.split('\n'):
			op, dist = line[0], int(line[1:])
			if op in 'LR':
				for _ in range(dist // 90 * (3 if op == 'L' else 1)):
					dx, dy = dy, -dx
			elif op == 'F':
				x += dx * dist
				y += dy * dist
			else:
				vx, vy = adventofcode.DIR[op]
				if mode:
					dx += vx * dist
					dy += vy * dist
				else:
					x += vx * dist
					y += vy * dist
		yield abs(x) + abs(y)

def day13(s):
	time, buses = s.split('\n')
	time = int(time)
	buses = [int(token) if token != 'x' else 0 for token in buses.split(',')]
	ans = min([((time + bus - 1) // bus * bus - time, bus) for bus in buses if bus])
	yield ans[0] * ans[1]
	a, p = 0, 1
	for i in range(len(buses)):
		b, q = -i, buses[i]
		if q:
			a, p = pow(p, -1, q) * (b - a) % q * p + a, p * q
	yield a

def day14(s):
	for mode in range(2):
		mem, mask_x, mask_ones = {}, 0, 0
		for line in s.split('\n'):
			var, value = line.split(' = ')
			if var == 'mask':
				mask_x, mask_ones = [int(value.replace(c, '0').replace('X', '1'), 2) for c in '1X']
				continue
			index, value = int(var[4:-1]), int(value)
			if not mode:
				mem[index] = value & mask_x | mask_ones
				continue
			index = (index | mask_ones) & ~mask_x
			mask = mask_x
			while True:
				mem[index | mask] = value
				if not mask:
					break
				mask = (mask - 1) & mask_x
		yield sum(mem.values())

def day15(s, ns=(2020, 30_000_000)):
	last, val = {}, 0
	for i in range(max(ns)):
		if i < len(s):
			val = s[i]
		if i + 1 in ns:
			yield val
		last[val], val = i, i - last[val] if val in last else 0

def day16(s, prefix='departure'):
	fields, my, nearby = s.split('\n\n')
	fields = fields.split('\n')
	my = list(map(int, my.split('\n')[1].split(',')))
	nearby = [list(map(int, line.split(','))) for line in nearby.split('\n')[1:]]
	ranges = []
	for line in fields:
		valid = set()
		for r in [token for token in line.split() if '-' in token]:
			a, b = map(int, r.split('-'))
			valid.update(range(a, b + 1))
		ranges.append(valid)
	all_ranges = functools.reduce(set.union, ranges)
	error_sum = 0
	possible = [[1] * len(ranges) for _ in range(len(ranges))]
	for ticket in nearby:
		errors = [x for x in ticket if x not in all_ranges]
		if errors:
			error_sum += sum(errors)
			continue
		for i in range(len(ranges)):
			for j in range(len(ranges)):
				if ticket[i] not in ranges[j]:
					possible[i][j] = 0
	yield error_sum
	order = scipy.optimize.linear_sum_assignment(possible, True)[1]
	yield functools.reduce(int.__mul__, [my[i] for i in range(len(my)) if fields[order[i]].startswith(prefix)])

def day17(s, n=6):
	s = s.split('\n')
	init = set()
	for i in range(len(s)):
		for j in range(len(s[i])):
			if s[i][j] == '#':
				init.add((i, j, 0, 0))
	def nei(active, x, y, z, w):
		count = 0
		for dx in range(-1, 2):
			for dy in range(-1, 2):
				for dz in range(-1, 2):
					for dw in range(-1, 2):
						if not (dx == dy == dz == dw == 0) and (x + dx, y + dy, z + dz, w + dw) in active:
							count += 1
		return count
	def run(active, mode):
		for _ in range(n):
			new = set()
			for x in range(-n - 1, n + len(s) + 2):
				for y in range(-n - 1, n + len(s[0]) + 2):
					for z in range(-n - 1, n + 2):
						for w in range(-n - 1, n + 2):
							if w and not mode:
								continue
							want = [2, 3] if (x, y, z, w) in active else [3]
							if nei(active, x, y, z, w) in want:
								new.add((x, y, z, w))
			active = new
		return len(active)
	yield run(init, False)
	yield run(init, True)

def day18(s):
	class WeirdInt(int):
		def __add__(self, other):
			return WeirdInt(int(self) + other)
		def __mul__(self, other):
			return self + other
		def __sub__(self, other):
			return WeirdInt(int(self) * other)
	ans1, ans2 = 0, 0
	for line in s.split('\n'):
		line = re.sub('\\d+', lambda match: WeirdInt.__name__ + "(" + match.group() + ")", line)
		line = line.replace('*', '-')
		ans1 += eval(line)
		ans2 += eval(line.replace('+', '*'))
	yield ans1
	yield ans2

def day19(s):
	rules, lines = [part.split('\n') for part in s.split('\n\n')]
	rules = dict([rule.split(': ') for rule in rules])
	memo = {}
	def matches(rule, string):
		if (rule, string) in memo:
			return memo[(rule, string)]
		result = False
		for prod in rules[rule].split(' | '):
			if prod.startswith('"'):
				result = result or prod == '"' + string + '"'
				continue
			prod = prod.split(' ')
			if len(prod) == 1:
				result = result or matches(prod[0], string)
				continue
			for i in range(1, len(string)):
				result = result or matches(prod[0], string[:i]) and matches(prod[1], string[i:])
		memo[(rule, string)] = result
		return result
	for _ in range(2):
		yield sum([matches('0', line) for line in lines])
		rules['8'] = '42 | 42 8'
		rules['11'] = '42 31 | 42 !'
		rules['!'] = '11 31'
		memo = {}

def day20(s, pattern=('..................#.', '#....##....##....###', '.#..#..#..#..#..#...')):
	connect, tiles = {}, {}
	for tile in s.split('\n\n'):
		name, *tile = tile.split('\n')
		name = int(name.replace('Tile ', '').replace(':', ''))
		borders = [tile[0], tile[-1], ''.join([row[0] for row in tile]), ''.join([row[-1] for row in tile])]
		tiles[name] = tile
		for row in borders:
			row = min(row, row[::-1])
			if row not in connect:
				connect[row] = []
			connect[row].append(name)
	nei = {}
	for pair in connect.values():
		if len(pair) < 2:
			continue
		for i in range(2):
			if pair[i] not in nei:
				nei[pair[i]] = []
			nei[pair[i]].append(pair[1 - i])
	corners, edge, middle = [[name for name in nei if len(nei[name]) == x] for x in range(2, 5)]
	yield functools.reduce(int.__mul__, corners)
	placed = set()
	top = [corners[0]]
	placed.add(corners[0])
	top_loop = True
	while top_loop:
		v = top[-1]
		u = [u for u in nei[v] if u in edge and u not in placed]
		if not u:
			top_loop = False
			u = [u for u in nei[v] if u in corners and u not in placed]
		top.append(u[0])
		placed.add(u[0])
	order = [top]
	while True:
		row = []
		for i in range(len(top)):
			v = order[-1][i]
			u = [u for u in nei[v] if u not in placed]
			if not u:
				break
			row.append(u[0])
			placed.add(u[0])
		if not row:
			break
		order.append(row)
	def flipped(r):
		return [''.join([r[y][x] for y in range(len(r))]) for x in range(len(r[0]))]
	def rotated(r):
		return [''.join([r[len(r) - 1 - y][x] for y in range(len(r))]) for x in range(len(r[0]))]
	def transformed(r, m):
		if m // 4:
			r = flipped(r)
		for _ in range(m % 4):
			r = rotated(r)
		return r
	for i in range(len(order) - 1):
		for j in range(len(order[0])):
			u, v, done = order[i][j], order[i + 1][j], False
			for a in range(7, -1, -1):  # or range(8)
				u_tile = transformed(tiles[u], a)
				if i and a:
					continue
				if not i and j:
					w = order[0][j - 1]
					w_tile = tiles[w]
					if not all([w_tile[x][-1] == u_tile[x][0] for x in range(len(u_tile))]):
						continue
				for b in range(8):
					v_tile = transformed(tiles[v], b)
					if u_tile[-1] != v_tile[0]:
						continue
					tiles[u], tiles[v], done = u_tile, v_tile, True
					break
				if done:
					break
			if not done:
				raise Exception('Change order of a')
	tile = tiles[corners[0]]
	field = [''] * (len(tile) - 2) * len(order)
	for i in range(len(order)):
		for j in range(len(order[0])):
			tile = tiles[order[i][j]]
			for x in range(1, len(tile) - 1):
				field[i * (len(tile) - 2) + x - 1] += tile[x][1:-1]
	def count(r):
		res = 0
		for x in range(len(r) - len(pattern) + 1):
			for y in range(len(r[0]) - len(pattern[0]) + 1):
				res += all([re.fullmatch(pattern[z], r[x + z][y:y + len(pattern[0])]) for z in range(len(pattern))])
		return res
	found = max([count(transformed(field, a)) for a in range(8)])
	yield ''.join(field).count('#') - found * ''.join(pattern).count('#')

def day21(s):
	data = [[part.split(' ') for part in line[:-1].replace(',', '').split(' (contains ')]
			for line in s.split('\n')]
	allergens = sorted(set(sum([d[1] for d in data], [])))
	possible = {allergen:
					list(functools.reduce(set.intersection, [set(d[0]) for d in data if allergen in d[1]]))
				for allergen in allergens}
	all_possible = sorted(set(sum(possible.values(), [])))
	yield len([x for x in sum([d[0] for d in data], []) if x not in all_possible])
	contains = [[possible[a].count(i) for i in all_possible] for a in allergens]
	order = scipy.optimize.linear_sum_assignment(contains, True)[1]
	yield ','.join([all_possible[x] for x in order])

def day22(s):
	s = [list(map(int, hand.split('\n')[1:])) for hand in s.split('\n\n')]
	while s[0] and s[1]:
		a, *s[0] = s[0]; b, *s[1] = s[1]
		s[a < b].extend(sorted([a, b], reverse=True))
	s = list(reversed(s[0] + s[1]))
	yield sum([s[i] * (i + 1) for i in range(len(s))])


if __name__ == '__main__':
	adventofcode.run()
