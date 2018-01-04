#!/usr/bin/python3 -B
import data

def day1(s):
	yield s.count('(') - s.count(')')

def day2(s):
	sum = 0
	for line in s.split():
		a, b, c = sorted(map(int, line.split('x')))
		sum += 3 * a * b + 2 * c * (a + b)
	yield sum
d = data.y2015
for i in range(len(d) - 1, -1, -1):
	day = 'day' + str(i + 1)
	print(day, *eval(day)(d[i]))
