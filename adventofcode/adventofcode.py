#!/usr/bin/env python3
import json
import os
import requests
import sys

sys.setrecursionlimit(max(10 ** 6, sys.getrecursionlimit()))

def run(year=None):
	if not year:
		import __main__
		year = os.path.basename(__main__.__file__)[1:5]
	year = str(year)
	year_module = __import__('y' + year)
	data = load_data()[year]
	for i in range(len(data) - 1, -1, -1):
		day = 'day' + str(i + 1)
		f = getattr(year_module, day)
		print(day, end=': ')
		for ans in f(data[i]):
			print(ans, end=' ', flush=True)
		print()

def load_data(filename='data~.json', url='https://pastebin.com/raw/xGvU9SZY'):
	if not os.path.isfile(filename):
		with open(filename, 'w') as f:
			f.write(json.dumps(requests.get(url).json(), indent=1))
	return json.load(open(filename, 'r'))

DIR = {'R': (1, 0), 'U': (0, 1), 'L': (-1, 0), 'D': (0, -1),
	   '>': (1, 0), '^': (0, 1), '<': (-1, 0), 'v': (0, -1)}

class AttrDict(dict):
    def __init__(self, *args, **kwargs):
        super(AttrDict, self).__init__(*args, **kwargs)
        self.__dict__ = self

def show_pixels(pixels):
	pixels = set(pixels)
	minx, maxx, miny, maxy = [f(zs) for zs in zip(*pixels) for f in (min, max)]
	pixels = [[((x, y) in pixels) for x in range(minx, maxx + 1)] for y in range(miny, maxy + 1)]
	return '\n'.join([''] + [''.join([('#' if pixel else ' ') for pixel in row]) for row in pixels])

if __name__ == '__main__':
	for year in load_data():
		print(year)
		run(year)
