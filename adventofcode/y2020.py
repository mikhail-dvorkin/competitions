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


if __name__ == '__main__':
    adventofcode.run()
