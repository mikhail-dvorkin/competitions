#!/usr/bin/env python3
import adventofcode


def day1(s):
	s = list(map(int, s.split('\n')))
	yield len([(a, b) for (a, b) in zip(s, s[1:]) if a < b])


if __name__ == '__main__':
	adventofcode.run()
