#!/usr/bin/python3
import sys

def ask(x):
	print('?', x)
	sys.stdout.flush()
	return 'Y' in input()

def output(x):
	print('!', x)
	sys.stdout.flush()
	if 'D' in input():
		exit()

def solve_ineffective(n):
	low, high = 1, n + 1
	while low + 1 < high:
		mid = (low + high) // 2
		response = ask(mid)
		if response != ask(mid):
			response = ask(mid)
		if response:
			low = mid
		else:
			high = mid
	output(low)

memo = {}
how = {}

def calc(t, fl=0, fr=0):
	if t + fl + fr == 1:
		return 0
	if t == 0:
		fl, fr = fl + fr, 0
	if (t, fl, fr) in memo:
		return memo[(t, fl, fr)]
	best = 3 * (t + fl + fr + 1)
	for i in range(1, t + fl + fr + 1 - 1):
		t1 = min(max(i - fl, 0), t)
		t2 = t - t1
		fl1 = min(i, fl)
		fl2 = fl - fl1
		fr1 = max(i - fl - t, 0)
		fr2 = fr - fr1
		s1, s2 = (t1, fl1, t2 + fr1), (t2, fl2 + t1, fr2)
		moves = max(calc(*s1), calc(*s2))
		if moves < best:
			best, best_move = moves, i
	memo[(t, fl, fr)] = 1 + best
	how[(t, fl, fr)] = best_move
	return 1 + best

def solve(n):
	t, fl, fr = list(range(1, n + 1)), [], []
	while len(t + fl + fr) > 1:
		if t == []:
			fl, fr = fl + fr, []
		move = how[(len(t), len(fl), len(fr))]
		move = (fl + t + fr)[move]
		response = ask(move)
		if response:
			fr = [x for x in fr if x >= move]
			fl = [x for x in fl if x >= move] + [x for x in t if x < move]
			t = [x for x in t if x >= move]
		else:
			fl = [x for x in fl if x < move]
			fr = [x for x in fr if x < move] + [x for x in t if x >= move]
			t = [x for x in t if x < move]
	output(*(t + fl + fr))

#print([{n: calc(n, 0, 0)} for n in range(2, 51)])
n = int(input())
calc(n)
while True:
	solve(n)
