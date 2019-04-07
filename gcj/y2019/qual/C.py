#!/usr/bin/python3
import math

def solve():
	input()
	data = list(map(int, input().split()))
	i = 0
	while data[i] == data[i + 1]:
		i += 1
	p = math.gcd(data[i], data[i + 1])
	ps = set([p])
	for segment in (data[i + 1:], data[i::-1]):
		q = p
		for x in segment:
			q = x // q
			ps.add(q)
	ps = sorted(ps)
	message = [q]
	for x in data:
		message.append(x // message[-1])
	return ''.join([chr(ord('A') + ps.index(x)) for x in message])

tests = int(input())
for t in range(tests):
	print('Case #{}: {}'.format(t + 1, solve()))
