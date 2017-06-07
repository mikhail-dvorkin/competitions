n = int(input())
s = [input() for i in range(n)]
m = len(s[0])
for i in range(n):
    good = True
    for j in range(n):
        diff = 0
        for k in range(m):
            if s[i][k] != s[j][k]:
                diff += 1
        if diff > 1:
            good = False
    if good:
        print(s[i])
        exit()
