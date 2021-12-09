#!/usr/bin/env python3
import adventofcode
import collections
import functools
import itertools


def day1(s, windows=[1, 3]):
	s = list(map(int, s.split('\n')))
	def increases(array):
		return sum([a < b for (a, b) in zip(array, array[1:])])
	for window in windows:
		yield increases([sum(s[i:i + window]) for i in range(len(s) - window + 1)])

def day2(s):
	DIR = {'forward': (1, 0), 'up': (0, 1), 'down': (0, -1)}
	s = s.split('\n')
	for mode in range(2):
		x, y, aim = 0, 0, 0
		for line in s:
			d, length = line.split()
			length = int(length)
			dx, dy = [length * z for z in DIR[d]]
			if mode:
				aim += dy
				x += dx; y += dx * aim
			else:
				x += dx; y += dy
		yield -x * y

def day3(s):
	s = s.split()
	def most_common(column):
		return sorted(column)[len(column) // 2]
	def to_int(row):
		return int(''.join(row), 2)
	common = [most_common(column) for column in zip(*s)]
	x = to_int(common)
	yield x * (2 ** len(s[0]) - 1 - x)
	def process(mode, a=s, x=0):
		if len(a) == 1:
			return to_int(a[0])
		bit = most_common([row[x] for row in a])
		a = [row for row in a if (row[x] == bit) ^ mode]
		return process(mode, a, x + 1)
	yield process(0) * process(1)

def day4(s):
	drawn, *s = s.split('\n\n')
	drawn = list(map(int, drawn.split(',')))
	when = {drawn[i]: i for i in range(len(drawn))}
	wins = []
	for board in s:
		board = [list(map(int, line.split())) for line in board.split('\n')]
		lines = board + list(zip(*board))
		win = min([max([when[x] for x in line]) for line in lines])
		wins.append((win, sum([x for x in sum(board, []) if when[x] > win])))
	for f in min, max:
		win = f(wins)
		yield drawn[win[0]] * win[1]

def day5(s):
	s = s.split('\n')
	count, count_straight = [collections.defaultdict(lambda: 0) for _ in range(2)]
	for line in s:
		x1, y1, x2, y2 = map(int, line.replace(' -> ', ',').split(','))
		dx, dy = map(adventofcode.signum, [x2 - x1, y2 - y1])
		x, y = x1, y1
		while True:
			count[(x, y)] += 1
			if dx == 0 or dy == 0:
				count_straight[(x, y)] += 1
			if (x, y) == (x2, y2):
				break
			x += dx; y += dy
	for c in count_straight, count:
		yield len([p for p in c if c[p] > 1])

def day6(s, days={80, 256}, new=(7, 9)):
	s = list(map(int, s.split(',')))
	count = collections.defaultdict(lambda: 0)
	for x in s:
		count[x] += 1
	for day in range(max(days) + 1):
		if day in days:
			yield sum(count.values())
		for add in new:
			count[day + add] += count[day]
		del count[day]

def day7(s):
	s = sorted(map(int, s.split(',')))
	for f in abs, lambda d: abs(d) * (abs(d) + 1) // 2:
		yield min([sum([f(x - c) for x in s]) for c in range(s[0], s[-1] + 1)])

def day8(s, easy=(1, 4, 7, 8)):
	digits = adventofcode.DIGITAL
	wires = [chr(ord('a') + i) for i in range(len(digits[0]))]
	reads = {}
	for p in itertools.permutations(wires):
		read = {}
		for d in range(len(digits)):
			shown = ''.join(sorted([p[i] for i in range(len(digits[d])) if digits[d][i] == '+']))
			read[shown] = d
		reads[tuple(sorted(read))] = read
	numbers = []
	for case in s.split('\n'):
		a, b = [[''.join(sorted(token)) for token in part.split()] for part in case.split(' | ')]
		read = reads[tuple(sorted(a))]
		numbers.append([read[token] for token in b])
	yield len([x for x in sum(numbers, []) if x in easy])
	yield sum([int(''.join(map(str, number))) for number in numbers])

def day9(s):
	s = [list(map(int, line)) for line in s.split()]
	hei, wid = len(s), len(s[0])
	mark = [[s[x][y] == 9 for y in range(wid)] for x in range(hei)]
	def dfs(x, y, h):
		mark[x][y] = True
		h.append(s[x][y])
		for dx, dy in adventofcode.DIRS[:4]:
			xx = x + dx; yy = y + dy
			if 0 <= xx < len(s) and 0 <= yy < len(s[0]) and not mark[xx][yy]:
				dfs(xx, yy, h)
	basins = []
	for x in range(hei):
		for y in range(wid):
			if mark[x][y]:
				continue
			basins.append([])
			dfs(x, y, basins[-1])
	yield sum([min(basin) + 1 for basin in basins])
	yield functools.reduce(int.__mul__, sorted(map(len, basins))[-3:])


if __name__ == '__main__':
	adventofcode.run()
