def solve(a):
    s = sum(a)
    if s == 0:
        return len(a) - 1
    if s % 2:
        return 0
    s = - s // 2
    for i in range(len(a)):
        s += a[i]
        if s > 0:
            return 0
        if s == 0:
            return 1 + max(solve(a[:i + 1]), solve(a[i + 1:]))


for _ in range(int(input())):
    input()
    a = list(map(int, input().split()))
    print(solve(a))
