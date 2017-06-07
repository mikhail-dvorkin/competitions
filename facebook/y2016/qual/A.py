import os.path

def solve():
    n = int(fin.readline())
    x = []
    y = []
    for i in range(n):
        xi, yi = map(int, fin.readline().split())
        x.append(xi)
        y.append(yi)
    ans = 0
    for i in range(n):
        dists = {}
        for j in range(n):
            if i == j:
                continue
            d = (x[i] - x[j]) ** 2 + (y[i] - y[j]) ** 2
            if d not in dists:
                dists[d] = 0
            dists[d] += 1
        for v in dists.values():
           ans += v * (v - 1) // 2
    return ans

fileName = os.path.splitext(os.path.basename(__file__))[0].lower()
fin = open(fileName + ".in")
fout = open(fileName + ".out", "w")

tests = int(fin.readline())
for t in range(tests):
    ans = "Case #{}: {}".format(t + 1, solve())
    print(ans)
    fout.write(ans + "\n")

fin.close()
fout.close()
