#!/usr/bin/python3
import sys

def solve():
	n, b, f = map(int, input().split())
	output, data = [], []
	for k in range(f):
		output.append(''.join([str((i >> (f - 1 - k)) & 1) for i in range(n)]))
		print(output[k])
		sys.stdout.flush()
		data.append(input() + '$')
	broken = []
	for i in range(n):
		if any([data[k][i - len(broken)] != output[k][i] for k in range(f)]):
			broken.append(i)
	print(*broken)
	input()

tests = int(input())
for t in range(tests):
	solve()
