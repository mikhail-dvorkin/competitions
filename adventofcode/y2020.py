#!/usr/bin/env python3
import adventofcode
import functools
import re


def day1(s, n=2020):
    s = set(map(int, s.split('\n')))
    def find(collection, desired_sum):
        return [x for x in collection if desired_sum - x in collection]
    for ans in find(s, n), [x for x in s if find(s - {x}, n - x)]:
        yield functools.reduce(int.__mul__, ans)

def day2(s):
    good1, good2 = 0, 0
    for line in s.split('\n'):
        range_str, letter_str, password = line.split()
        low, high = map(int, range_str.split("-"))
        letter = letter_str[0]
        if low <= password.count(letter) <= high:
            good1 += 1
        if (password[low - 1] + password[high - 1]).count(letter) == 1:
            good2 += 1
    yield good1
    yield good2

def day3(s, dirs=(((3, 1),), ((1, 1), (3, 1), (5, 1), (7, 1), (1, 2)))):
    s = s.split('\n')
    def count(dx, dy):
        return len([1 for i in range(len(s)) if i % dy == 0 and s[i][i * dx // dy % len(s[i])] == '#'])
    for dir_list in dirs:
        yield functools.reduce(int.__mul__, [count(dx, dy) for dx, dy in dir_list])

def day4(s):
    checks = {
        'byr': r'19[2-9]\d|200[0-2]',
        'iyr': r'201\d|2020',
        'eyr': r'202\d|2030',
        'hgt': r'(1[5-8]\d|19[0-3])cm|(59|6\d|7[0-6])in',
        'hcl': r'#[0-9a-f]{6}',
        'ecl': r'amb|blu|brn|gry|grn|hzl|oth',
        'pid': r'\d{9}',
    }
    ans1, ans2 = 0, 0
    for entry in s.split('\n\n'):
        passport = dict([t.split(':') for t in entry.split()])
        if set(passport).issuperset(checks.keys()):
            ans1 += 1
            ans2 += all([re.fullmatch(regex, passport[key]) for key, regex in checks.items()])
    yield ans1
    yield ans2

def day5(s, trans=("FB" + "LR", "0101")):
    s = [int(x.translate(str.maketrans(*trans)), 2) for x in s.split('\n')]
    yield max(s)
    yield min(set(range(min(s), max(s) + 1)) - set(s))

def day6(s):
    s = [[set(line) for line in group.split('\n')] for group in s.split('\n\n')]
    for op in set.union, set.intersection:
        yield sum([len(functools.reduce(op, group)) for group in s])

def day7(s, my='shiny gold'):
    dag = {}
    for line in s.split('\n'):
        a, b = line.split(' bags contain ')
        dag[a] = {}
        for term in b.split(','):
            c, d, e, *_ = term.split()
            if c != 'no':
                dag[a][d + " " + e] = int(c)
    def good(v):
        return True if v == my else any([good(u) for u in dag[v]])
    yield len([v for v in dag if good(v)]) - 1
    def size(v):
        return 1 + sum([dag[v][u] * size(u) for u in dag[v]])
    yield size(my) - 1

def day8(s):
    s = s.split('\n')
    def exec_assembler(changed=-1):
        i = 0
        acc = 0
        executed = set()
        while i not in executed and i < len(s):
            op, arg = s[i].split()
            if i == changed:
                if op == 'nop':
                    op = 'jmp'
                elif op == 'jmp':
                    op = 'nop'
            arg = int(arg)
            executed.add(i)
            i += 1
            if op == 'acc':
                acc += arg
            elif op == 'jmp':
                i += arg - 1
        return i == len(s), acc
    yield exec_assembler()[1]
    yield max([exec_assembler(changed) for changed in range(len(s))])[1]

def day9(s, m=25):
    s, i = [int(x) for x in s.split('\n')], None
    for i in range(m, len(s)):
        prev = set(s[i - m:i])
        if not any([s[i] - x in prev for x in prev]):
            break
    j, k, diff = 0, 0, s[i]
    yield diff
    while diff:
        if diff > 0:
            diff -= s[k]
            k += 1
        else:
            diff += s[j]
            j += 1
    yield min(s[j:k]) + max(s[j:k])

def day10(s):
    s = sorted([int(x) for x in s.split('\n')])
    s = [0] + s + [max(s) + 3]
    diffs = [b - a for a, b in zip(s, s[1:])]
    yield diffs.count(1) * diffs.count(3)
    a = [1]
    for i in range(s[-1]):
        a.append(sum(a[-3:]) * (i + 1 in s))
    yield a[-1]

def day11(s, parameters=((4, False), (5, True))):
    s = s.split('\n')
    hei, wid = len(s), len(s[0])
    def model(field, threshold, long):
        field_next = [list(row) for row in field]
        for i in range(hei):
            for j in range(wid):
                nei = 0
                for di in range(-1, 2):
                    for dj in range(-1, 2):
                        if di == dj == 0:
                            continue
                        ii, jj = i, j
                        while True:
                            ii, jj = ii + di, jj + dj
                            if not (long and 0 <= ii < hei and 0 <= jj < wid and field[ii][jj] == '.'):
                                break
                        if 0 <= ii < hei and 0 <= jj < wid and field[ii][jj] == '#':
                            nei += 1
                if field[i][j] == 'L' and nei == 0:
                    field_next[i][j] = '#'
                if field[i][j] == '#' and nei >= threshold:
                    field_next[i][j] = 'L'
        if field_next == field:
            return sum(field, []).count('#')
        return model(field_next, threshold, long)
    for p in parameters:
        yield model(s, *p)

def day12(s):
    for mode in range(2):
        x, y = 0, 0
        dx, dy = (10, 1) if mode else adventofcode.DIR['E']
        for line in s.split('\n'):
            op, dist = line[0], int(line[1:])
            if op in 'LR':
                for _ in range(dist // 90 * (3 if op == 'L' else 1)):
                    dx, dy = dy, -dx
            elif op == 'F':
                x += dx * dist
                y += dy * dist
            else:
                vx, vy = adventofcode.DIR[op]
                if mode:
                    dx += vx * dist
                    dy += vy * dist
                else:
                    x += vx * dist
                    y += vy * dist
        yield abs(x) + abs(y)

def day13(s):
    time, buses = s.split('\n')
    time = int(time)
    buses = [int(token) if token != 'x' else 0 for token in buses.split(',')]
    ans = min([((time + bus - 1) // bus * bus - time, bus) for bus in buses if bus])
    yield ans[0] * ans[1]
    a, p = 0, 1
    for i in range(len(buses)):
        b, q = -i, buses[i]
        if q:
            a, p = pow(p, -1, q) * (b - a) % q * p + a, p * q
    yield a

def day14(s):
    for mode in range(2):
        mem, mask_x, mask_ones = {}, 0, 0
        for line in s.split('\n'):
            var, value = line.split(' = ')
            if var == 'mask':
                mask_x, mask_ones = [int(value.replace(c, '0').replace('X', '1'), 2) for c in '1X']
                continue
            index, value = int(var[4:-1]), int(value)
            if not mode:
                mem[index] = value & mask_x | mask_ones
                continue
            index = (index | mask_ones) & ~mask_x
            mask = mask_x
            while True:
                mem[index | mask] = value
                if not mask:
                    break
                mask = (mask - 1) & mask_x
        yield sum(mem.values())


if __name__ == '__main__':
    adventofcode.run()
