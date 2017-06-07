#!/usr/bin/python3
import os.path


def solve():
	s, k = fin.readline().split()
	k = int(k)
	s = [c == '+' for c in s]
	n = len(s)
	ans = 0
	for i in range(n):
		if s[i]:
			continue
		if i + k > n:
			return "IMPOSSIBLE"
		for j in range(k):
			s[i + j] ^= True
		ans += 1
	return ans


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
