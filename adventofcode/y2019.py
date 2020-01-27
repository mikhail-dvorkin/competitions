#!/usr/bin/env python3
import adventofcode
import collections
import functools
import itertools
import math
import re
import threading

def exec_assembler(s, input=(), output=()):
	if not hasattr(input, 'popleft'):
		input = collections.deque(input)
	if not hasattr(output, 'append'):
		output = list(output)
	if isinstance(s, str):
		s = list(map(int, s.split(',')))
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
		99: lambda: exit
	}
	def ensure_address(address, mode):
		if mode == 2:
			address += rel_base
		s.extend([0] * max(address + 1 - len(s), 0))
		return address
	i, rel_base = 0, 0
	while i < len(s):
		mode, op, jump_to, rel_base_inc = s[i] // 100, operations[s[i] % 100], [], [0]
		i += 1
		args = s[i:i + op.__code__.co_argcount]
		i += len(args)
		for j in range(len(args)):
			if mode % 10 in {0, 2}:
				args[j] = s[ensure_address(args[j], mode % 10)]
			mode //= 10
		res = op(*args)
		if res is exit:
			break
		if res is not None:
			s[ensure_address(s[i], mode)] = res
			i += 1
		if jump_to:
			i = jump_to[0]
		rel_base += rel_base_inc[-1]
	return output

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
	low, high = s
	high += 1
	f1 = lambda s: min([ord(y) - ord(x) for x, y in zip(s, s[1:])]) == 0
	f2 = lambda s: f1(s) and 2 in [len(list(group)) for _, group in itertools.groupby(s)]
	for f in f1, f2:
		yield len(list(filter(f, map(str, range(low, high)))))

def day5(s, args=(1, 5)):
	for arg in args:
		yield exec_assembler(s, [arg])[-1]

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
	def check(p):
		pipe = [collections.deque([x]) for x in p]
		pipe[0].append(0)
		threads = [threading.Thread(target=exec_assembler, args=[s, pipe[i], pipe[(i + 1) % n]]) for i in range(n)]
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
	yield adventofcode.show_pixels([(i % w, i // w) for i in range(w * h) if image[i] == '1'])

def day9(s):
	yield from day5(s, (1, 2))

def day10(s, n=200):
	s = s.split('\n')
	s = [(x, y) for y in range(len(s)) for x in range(len(s[y])) if s[y][x] == '#']
	def process(x0, y0):
		dirs = {}
		for x, y in s:
			dx = x - x0; dy = y - y0
			if (dx, dy) == (0, 0):
				continue
			gcd = math.gcd(dx, dy)
			dx //= gcd; dy //= gcd
			dirs.setdefault((dx, dy), []).append((x, y))
		return len(dirs), x0, y0, dirs
	best, x0, y0, dirs = max([process(x, y) for x, y in s])
	yield best
	s = []
	for v, ray in dirs.items():
		ray.sort(key=lambda xy: abs(xy[0] - x0) + abs(xy[1] - y0))
		s.extend([(7 * i - math.atan2(*v), *ray[i]) for i in range(len(ray))])
	item = sorted(s)[n - 1]
	yield item[1] * 100 + item[2]

def day11(s):
	env = None
	def current_color():
		return 1 if (env.x, env.y) in env.board else 0
	def process_output(bit):
		if env.mode == 0:
			if bit:
				env.board.add((env.x, env.y))
				env.memo.add((env.x, env.y))
			else:
				env.board.discard((env.x, env.y))
		else:
			env.dx, env.dy = (-env.dy, env.dx) if bit else (env.dy, -env.dx)
			env.x += env.dx; env.y += env.dy
		env.mode ^= 1
	def run(init):
		nonlocal env
		env = adventofcode.AttrDict(board=set(init), memo=set(init), x=0, y=0, dx=0, dy=-1, mode=0, popleft=current_color, append=process_output)
		exec_assembler(s, env, env)
		return env
	yield len(run([]).memo)
	yield adventofcode.show_pixels(run([(0, 0)]).board)

def day12(s, t=1000):
	r = [list(map(int, re.fullmatch('<x=(-?\\d+), y=(-?\\d+), z=(-?\\d+)>', line).groups())) for line in s.split('\n')]
	v = [[0, 0, 0] for _ in r]
	seen_states, periods = [{} for _ in range(3)], [0] * 3
	for step in itertools.count():
		for c in range(3):
			if not periods[c]:
				state = tuple([a[c] for a in r + v])
				if state in seen_states[c]:
					assert seen_states[c][state] == 0
					periods[c] = step
				seen_states[c][state] = step
			for i in range(len(r)):
				v[i][c] += sum([adventofcode.signum(other[c] - r[i][c]) for other in r])
			for i in range(len(r)):
				r[i][c] += v[i][c]
		if step == t - 1:
			yield sum([sum(map(abs, r[i])) * sum(map(abs, v[i])) for i in range(len(r))])
		if all(periods):
			yield functools.reduce(lambda x, y: x * y // math.gcd(x, y), periods)
			break

def day13(s):
	def play():
		env.blocks.append(list(env.field.values()).count(2))
		xball, xpaddle = [[x for x, v in env.field.items() if v == item][0][0] for item in (4, 3)]
		return adventofcode.signum(xball - xpaddle)
	def get(x):
		env.buffer.append(x)
		if len(env.buffer) == 3:
			env.field[tuple(env.buffer[:2])] = env.buffer[2]
			env.buffer = []
	env = adventofcode.AttrDict(popleft=play, append=get, buffer=[], field={}, blocks=[])
	exec_assembler([2] + list(map(int, s.split(',')))[1:], env, env)
	yield env.blocks[0]
	yield env.field[(-1, 0)]

def day14(s, start='ORE', end='FUEL', desired=10**12):
	def parse(s):
		amount, chemical = s.split()
		return (chemical, int(amount))
	howto = {}
	for line in s.split('\n'):
		ingredients, result = line.split(' => ')
		chemical, amount = parse(result)
		howto[chemical] = (amount, list(map(parse, ingredients.split(', '))))
	def dfs(chemical=end, mark=set(), order=[]):
		mark.add(chemical)
		if chemical != start:
			for other, amount in howto[chemical][1]:
				if other not in mark:
					dfs(other, mark, order)
		order.append(chemical)
		return order
	order = dfs()
	def takes(x):
		need = [0] * (len(order) - 1) + [x]
		for i in range(len(order) - 1, 0, -1):
			get, ingredients = howto[order[i]]
			times = (need[i] + get - 1) // get
			for other, amount in ingredients:
				need[order.index(other)] += amount * times
		return need[0]
	yield takes(1)
	low = 0
	high = desired + 1
	while low + 1 < high:
		mid = (low + high) // 2
		if takes(mid) <= desired:
			low = mid
		else:
			high = mid
	yield low

def day15(s, dirs='NSWE'):
	input, output = [collections.deque() for _ in range(2)]
	thread = threading.Thread(target=exec_assembler, args=[s, input, output])
	thread.start()
	mark, walls = set(), set()
	def make_move(move):
		input.append(move + 1)
		while not output:
			pass
		return output.popleft()
	queue = collections.deque()
	def dfs(x=0, y=0):
		mark.add((x, y))
		for d in range(len(dirs)):
			dx, dy = adventofcode.DIR[dirs[d]]
			xx, yy = x + dx, y + dy
			if (xx, yy) in mark or (xx, yy) in walls:
				continue
			outcome = make_move(d)
			if not outcome:
				walls.add((xx, yy))
				continue
			if outcome == 2:
				queue.append((xx, yy))
			dfs(xx, yy)
			make_move(d ^ 1)
	dfs()
	input.append(exit)
	thread.join()
	dist = {queue[0]: 0}
	while queue:
		x, y = queue.popleft()
		for d in dirs:
			dx, dy = adventofcode.DIR[d]
			xx, yy = x + dx, y + dy
			if (xx, yy) not in mark or (xx, yy) in dist:
				continue
			queue.append((xx, yy))
			dist[(xx, yy)] = dist[(x, y)] + 1
	yield dist[(0, 0)]
	yield max(dist.values())

def day16(s, phases=100, modes=(1, 10000), size=8, offset_size=7):
	def process(a, _):
		cum = [0]
		for x in a:
			cum.append(cum[-1] + x)
		t = []
		for i in range(len(a)):
			if 2 * offset >= len(a) and i < offset:
				t.append(0)
				continue
			res = 0
			for j in range(len(a)):
				low = (i + 1) * (2 * j + 1) - 1
				if low >= len(a):
					break
				high = min((i + 1) * (2 * j + 2) - 1, len(a))
				res += (cum[high] - cum[low]) * (-1 if j % 2 else 1)
			t.append(abs(res) % 10)
		return t
	for mode in modes:
		offset = 0 if mode == 1 else int(s[:offset_size])
		a = functools.reduce(process, range(phases), list(map(int, s)) * mode)
		yield ''.join(map(str, a[offset:offset + size]))

def day17(s, dirs='^v<>', parts=3, maxlen=20):
	s = list(map(int, s.split(',')))
	field = ''.join(map(chr, exec_assembler(s[:]))).split(chr(10))
	res = 0
	def valid(x, y):
		return 0 <= x < len(field) and 0 <= y < len(field[x]) and field[x][y] == '#'
	for i in range(len(field)):
		for j in range(len(field[i])):
			intersection = field[i][j] == '#'
			for d in dirs:
				di, dj = adventofcode.DIR[d]
				ii, jj = i + di, j + dj
				if not valid(ii, jj):
					intersection = False
			if intersection:
				res += i * j
			if field[i][j] in dirs:
				x, y = i, j
	yield res
	dy, dx = adventofcode.DIR[field[x][y]];	dx *= -1
	route = []
	while True:
		if valid(x + dy, y - dx):
			dx, dy = dy, -dx
			route.append("R")
		elif valid(x - dy, y + dx):
			dx, dy = -dy, dx
			route.append("L")
		else:
			break
		t = 0
		while valid(x + dx, y + dy):
			t += 1; x += dx; y += dy
		route.append(t)
	def search(a, ps=[], way=[]):
		if not a:
			return ps, way
		goodp = []
		for p in ps:
			if len(p) <= len(a) and a[:len(p)] == p:
				goodp.append(p)
		if len(goodp) == 1:
			return search(a[len(goodp[0]):], ps, way + [ps.index(goodp[0])])
		if len(goodp) > 1 or len(ps) == parts:
			return None
		for i in range(1, len(a) + 1):
			if len(','.join(map(str, a[:i]))) > maxlen:
				break
			outcome = search(a[i:], ps + [a[:i]], way + [len(ps)])
			if outcome:
				return outcome
	ps, way = search(route)
	instr = [','.join([chr(ord('A') + x) for x in way])] + [','.join(map(str, a)) for a in ps] + ['n', '']
	yield exec_assembler([2] + s[1:], map(ord, chr(10).join(instr)))[-1]

def day18(s):
	s = s.split('\n')
	def graph():
		nei = {}
		for i in range(len(s)):
			for j in range(len(s[i])):
				if s[i][j] in '.#':
					continue
				nei[s[i][j]] = []
				dist = {(i, j): 0}
				queue = collections.deque([(i, j)])
				while queue:
					x, y = queue.popleft()
					if (x, y) != (i, j) and s[x][y] != '.':
						nei[s[i][j]].append((s[x][y], dist[(x, y)]))
						continue
					for dx, dy in adventofcode.DIRS[:4]:
						xx, yy = x + dx, y + dy
						if xx < 0 or xx >= len(s) or yy < 0 or yy >= len(s[xx]) or s[xx][yy] == '#' or (xx, yy) in dist:
							continue
						dist[(xx, yy)] = dist[(x, y)] + 1
						queue.append((xx, yy))
		return nei
	def solve(init):
		nei = graph()
		solved = sum([1 << (ord(v) - ord('a')) for v in nei if 'a' <= v <= 'z'])
		queue, dist = {}, {}
		def mark(mask, pos, d, shifts):
			u = (mask, *pos)
			if d < dist.get(u, d + 1):
				dist[u] = d
				queue.setdefault(d, []).append(u)
			if shifts > 1:
				mark(mask, pos[1:] + pos[:1], d, shifts - 1)
		mark(0, init, 0, len(init))
		for d in itertools.count():
			for mask, *pos in queue.get(d, []):
				if mask == solved:
					return d
				if dist[(mask, *pos)] != d:
					continue
				for u, edge in nei[pos[0]]:
					if 'A' <= u <= 'Z' and mask >> (ord(u) - ord('A')) & 1 == 0:
						continue
					new_mask = mask | (1 << (ord(u) - ord('a')) if 'a' <= u <= 'z' else 0)
					mark(new_mask, (u, *pos[1:]), d + edge, 1 if new_mask == mask else len(pos))
	yield solve('@')
	sx = ['@' in row for row in s].index(True)
	sy = s[sx].index('@')
	ins = ('0#1', '###', '2#3')
	for i in range(3):
		s[sx + i - 1] = s[sx + i - 1][:sy - 1] + ins[i] + s[sx + i - 1][sy + 2:]
	yield solve('0123')

def day19(s, sizes=(50, 100)):
	def check(x, y):
		return exec_assembler(s, [x, y])[0]
	yield sum([check(x, y) for x in range(sizes[0]) for y in range(sizes[0])])
	y = 0
	for x in itertools.count():
		while not check(x + sizes[1] - 1, y):
			y += 1
		if check(x, y + sizes[1] - 1):
			yield x * sizes[1] ** 2 + y
			break

def day20(s, start_end=('AA', 'ZZ')):
	s = s.split('\n')
	labels = {}
	for x in range(len(s)):
		for y in range(len(s[x])):
			if s[x][y] != '.':
				continue
			for dx, dy in adventofcode.DIRS[:4]:
				label = s[x + dx][y + dy] + s[x + 2 * dx][y + 2 * dy]
				if label.isalpha():
					labels.setdefault(''.join(sorted(label)), []).append((x, y))
	teleport = {}
	for u, v in filter(lambda pair: len(pair) == 2, labels.values()):
		outer = adventofcode.signum(min(v[0], v[1], len(s) - 1 - v[0], len(s[v[0]]) - 1 - v[1]) - 3)
		teleport[v], teleport[u] = (*u, outer), (*v, -outer)
	start, end = [(*labels[label][0], 0) for label in start_end]
	for mode in 0, 1:
		queue, dist = collections.deque([start]), {start: 0}
		while end not in dist:
			x, y, z = queue.popleft()
			nei = [(x + dx, y + dy, 0) for dx, dy in adventofcode.DIRS[:4]] + [teleport.get((x, y), (x, y, 0))]
			for xx, yy, dz in nei:
				dz *= mode
				if s[xx][yy] != '.' or (xx, yy, z + dz) in dist or z + dz < 0:
					continue
				queue.append((xx, yy, z + dz))
				dist[(xx, yy, z + dz)] = dist[(x, y, z)] + 1
		yield dist[end]

def day21(s):
	# d & (!a | !c)		d & (!a | !b | !c) & (e | h)
	p1 = 'NOT A J;NOT C T;OR T J;AND D J;WALK;'
	p2 = 'NOT A J;NOT B T;OR T J;NOT C T;OR T J;AND D J;NOT J T;OR E T;OR H T;AND T J;RUN;'
	for program in p1, p2:
		yield exec_assembler(s, map(ord, program.replace(';', chr(10))))[-1]

def day22(s, size1=10007, x1=2019, size2=119315717514047, times2=101741582076661, y2=2020):
	operations = {
		'deal_into_new': lambda a, b, _: (-a, -1 - b),
		'cut': lambda a, b, n: (a, b - n),
		'deal_with_increment': lambda a, b, n: (a * n, b * n)
	}
	def apply(ab, line):
		*command, arg = line.split()
		arg = int(arg) if not arg.isalpha() else 0
		return operations['_'.join(command)](*ab, arg)
	a, b = functools.reduce(apply, s.split('\n'), (1, 0))
	yield (a * x1 + b) % size1
	def multiply(a, b, c, d):
		return a * c % size2, b * c + d
	def power(n):
		if n % 2:
			return multiply(*power(n - 1), a, b) if n > 1 else (a, b)
		t = power(n // 2)
		return multiply(*t, *t)
	a, b = power(times2)
	yield (y2 - b) * pow(a, size2 - 2, size2) % size2

def day23(s, n=50):
	inputs = [collections.deque([i]) for i in range(n)]
	outputs = [collections.deque() for i in range(n)]
	to_nat, waiting = [], set()
	def append(id, value):
		outputs[id].append(value)
		if len(outputs[id]) >= 3:
			to_id, x, y = [outputs[id].popleft() for _ in range(3)]
			if to_id < n:
				inputs[to_id].extend([x, y])
				waiting.clear()
			elif to_id == 255:
				to_nat.extend([x, y])
	def popleft(id):
		if inputs[id]:
			return inputs[id].popleft()
		waiting.add(id)
		return -1
	def env(id):
		return adventofcode.AttrDict(append=lambda value, id=id: append(id, value), popleft=lambda id=id: popleft(id))
	def nat():
		prev = None
		while True:
			if len(waiting) == n and all([not input for input in inputs]):
				inputs[0].extend(to_nat[-2:])
				if to_nat[-1] == prev:
					break
				prev = to_nat[-1]
		[input.append(exit) for input in inputs]
	threads = [threading.Thread(target=exec_assembler, args=[s, env(i), env(i)]) for i in range(n)]
	threads.append(threading.Thread(target=nat))
	[t.start() for t in threads]
	[t.join() for t in threads]
	yield to_nat[1]
	yield to_nat[-1]

def day24(s, steps=200):
	n = len(s.split("\n"))
	s = s.replace("\n", "")
	field = sum([1 << i for i in range(n * n) if s[i] == '#'])
	def new_bug(bug, bugs):
		return 1 if bugs == 1 or not bug and bugs == 2 else 0
	seen = {field}
	while True:
		new_field = 0
		for x in range(n):
			for y in range(n):
				bugs = 0
				for dx, dy in adventofcode.DIRS[:4]:
					xx, yy = x + dx, y + dy
					if 0 <= xx < n and 0 <= yy < n and (field >> (n * xx + yy)) & 1:
						bugs += 1
				new_field += new_bug((field >> (n * x + y)) & 1, bugs) << (n * x + y)
		field = new_field
		if field in seen:
			break
		seen.add(field)
	yield field
	def are_nei(xi, yi, xo, yo):
		return {(xi, xo, yo), (yi, yo, xo)} & {(0, n // 2 - 1, n // 2), (n - 1, n // 2 + 1, n // 2)}
	field = [[[0 for _ in range(n)] for _ in range(n)] for _ in range(2 * steps + 3)]
	field[steps + 1] = [[1 if s[n * i + j] == '#' else 0 for j in range(n)] for i in range(n)]
	def next_field(x, y, z):
		if x == y == n // 2 or z in {0, 2 * steps + 2}:
			return 0
		bugs = 0
		for xx in range(n):
			for yy in range(n):
				good = [are_nei(xx, yy, x, y), abs(x - xx) + abs(y - yy) == 1, are_nei(x, y, xx, yy)]
				bugs += sum([1 for dz in range(-1, 2) if field[z + dz][xx][yy] and good[1 + dz]])
		return new_bug(field[z][x][y], bugs)
	for step in range(steps):
		field = [[[next_field(x, y, z) for x in range(n)] for y in range(n)] for z in range(2 * steps + 3)]
	yield sum(sum(sum(field, []), []))

def day25(s):
	p = 'north;take easter eggA;east;take astrolabeB;south;take space law space brochureC;'
	p += 'north;north;north;take fuel cellD;south;south;west;north;take manifoldE;'
	p += 'north;north;take hologramF;north;take weather machineG;north;take antennaH;west;south;'
	p = p.replace(';', chr(10))
	def append(v):
		output.append(chr(v))
		if ''.join(output[-5:]) == 'eject':
			input.extend([exit, 0])
	env = adventofcode.AttrDict(append=append)
	n = len(list(filter(str.isupper, p)))
	for mask in range(1 << n):
		program = p
		for i in range(n):
			if (mask >> i) & 1:
				program = program.replace(chr(ord('A') + i), '')
		input, output = collections.deque(map(ord, program)), []
		exec_assembler(s, input, env)
		if not input:
			break
	yield ''.join(filter(str.isdigit, output))

if __name__ == '__main__':
	adventofcode.run()
