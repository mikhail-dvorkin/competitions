M = 10**9 + 7

def solve(n):
    a = n
    i = 1
    while i < n:
        k = n // i
        j = min(n // k + 1, n)
        a += k * (j - i) * n - k * (k + 1) * (j - i) * (i + j - 1) // 4
        a %= M
        i = j
    return a


for _ in range(int(input())):
    n = int(input())
    print(solve(n))
