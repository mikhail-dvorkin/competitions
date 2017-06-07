input()
s = input()

prev = None
ans = 0
for c in s:
    if c != prev:
        ans += 1
    prev = c
print(ans)
