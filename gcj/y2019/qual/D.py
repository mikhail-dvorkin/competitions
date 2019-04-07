#!/usr/bin/python3
import sys

def solve():
	n, b, f = map(int, input().split())
	for k in range(f):
		group = 1 << (f - 1 - k)
		output = ''.join([str(i // group % 2) for i in range(n)])
		print(output)
		sys.stdout.flush()
		data = input()
		if k == 0:
			i_prev, broken = -1, []
			for i in range(len(data)):
				if i == len(data) - 1 or data[i] != data[i + 1]:
					broken.append(group - (i - i_prev))
					i_prev = i
			groups = (n - 1) // group + 1
			if len(broken) < groups:
				broken.append(n - group * (groups - 1))
			continue
		cum, broken_new = 0, []
		for i in range(len(broken)):
			segment = data[cum:cum + 2 * group - broken[i]]
			for c in '01':
				broken_new.append(group - segment.count(c))
			cum += len(segment)
		broken = broken_new
	print(*[i for i in range(n) if broken[i]])
	input()

tests = int(input())
for t in range(tests):
	solve()
