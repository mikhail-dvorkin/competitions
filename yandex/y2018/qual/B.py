#!/usr/bin/python3

n = int(input())
a = []
for i in range(2 * n):
	a.append(list(map(int, input().split())))
firsts = [row[0] for row in a]
start = sum(firsts) - sum(set(firsts))
left = firsts.index(start)
firsts[left] = None
ans = []
for v in a[left]:
	ans.extend(a[firsts.index(v)])
print(*ans)
