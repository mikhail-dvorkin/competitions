n, k = map(int, input().split())
contests = [tuple(map(int, input().split())) for _ in range(n)]

important = [c[0] for c in contests if c[1]]
important.sort()
win = sum(important[:-k])
s = sum([c[0] for c in contests])
print(s - 2 * win)
