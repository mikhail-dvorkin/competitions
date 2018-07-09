#!/usr/bin/python3

def read():
	return set(map(int, input().split()))

a = read()
for _ in range(int(input())):
	b = read()
	print('Lucky' if len(a.intersection(b)) >= 3 else 'Unlucky')
