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
	filename = "data~.json"
	if os.path.isfile(filename):
		with open("data~.json", "r") as read_file: data = json.load(read_file)
	else:
		data = requests.get('https://pastebin.com/raw/xGvU9SZY').json()
	data = data[year]
	for i in range(len(data) - 1, -1, -1):
		day = 'day' + str(i + 1)
		f = getattr(year_module, day)
		print(day, *f(data[i]))

if __name__ == '__main__':
	run(2019)
