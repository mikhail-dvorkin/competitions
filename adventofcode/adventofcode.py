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
			f.write(requests.get(url).text)
	return json.load(open(filename, 'r'))

if __name__ == '__main__':
	for year in load_data():
		print(year)
		run(year)
