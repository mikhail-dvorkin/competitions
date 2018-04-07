#!/usr/bin/python3
from math import atan, asin, sin, cos

def solve():
	area = float(input())
	a = asin(area * 3 ** -0.5) - atan(2 ** -0.5)
	s = 2 ** 0.5 / 4
	ans = -0.5 * sin(a), 0.5 * cos(a), 0,\
		s * cos(a), s * sin(a), s,\
		-s * cos(a), -s * sin(a), s
	return ans

tests = int(input())
for t in range(tests):
	print('Case #{}:\n{} {} {}\n{} {} {}\n{} {} {}'.format(t + 1, *solve()))
