#!/usr/bin/python3
import os.path


def solve():
	s = fin.readline().strip()
	x = s.find(s[0], 1)
	if x > 0:
		t = s[:x] + s
		if not t.startswith(s):
			return t
	return "Impossible"


filename = os.path.splitext(os.path.basename(__file__))[0].split('_')[0].lower()
fin = open(filename + '.in')
fout = open(filename + '.out', 'w')

tests = int(fin.readline())
for t in range(tests):
	ans = 'Case #{}: {}'.format(t + 1, solve())
	print(ans)
	fout.write(ans + '\n')

fin.close()
fout.close()
