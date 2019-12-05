#!/usr/bin/env python3
import itertools
import json
import os
import requests
import sys

DIR = {'R': (1, 0), 'U': (0, 1), 'L': (-1, 0), 'D': (0, -1)}

def exec_assembler(s, input=[], output=[]):
	i = 0
	while i < len(s):
		mode, op = s[i] // 100, s[i] % 100
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
		i += 1
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

def day5(s):
	s = list(map(int, s.split(',')))
	output = []
	exec_assembler(s, [1], output)
	yield output[-1]

if __name__ == '__main__':
	sys.setrecursionlimit(max(10 ** 6, sys.getrecursionlimit()))
	year = "2019"
	filename = "data~.json"
	if os.path.isfile(filename):
		with open("data~.json", "r") as read_file: d = json.load(read_file)
	else:
		d = requests.get('https://pastebin.com/raw/xGvU9SZY').json()
	d = d[year]
	for i in range(len(d) - 1, -1, -1):
		day = 'day' + str(i + 1)
		print(day, *eval(day)(d[i]))
