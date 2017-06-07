from collections import Counter


def solve():
	input()
	s = input()
	if '_' in s:
		d = dict(Counter(s))
		del d['_']
		return 1 not in d.values()
	s = '_' + s + '_'
	for i in range(1, len(s) - 1):
		if s[i - 1] != s[i] != s[i + 1]:
			return False
	return True


for _ in range(int(input())):
	print("YES" if solve() else "NO")
