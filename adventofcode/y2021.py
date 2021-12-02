#!/usr/bin/env python3
import adventofcode


def day1(s, windows=[1, 3]):
	s = list(map(int, s.split('\n')))
	def increases(array):
		return sum([a < b for (a, b) in zip(array, array[1:])])
	for window in windows:
		yield increases([sum(s[i:i + window]) for i in range(len(s) - window + 1)])

def day2(s):
	DIR = {'forward': (1, 0), 'up': (0, 1), 'down': (0, -1)}
	s = s.split('\n')
	x, y = 0, 0
	for line in s:
		d, length = line.split()
		length = int(length)
		dx, dy = [length * z for z in DIR[d]]
		x += dx; y += dy
	yield x * y


if __name__ == '__main__':
	adventofcode.run()
