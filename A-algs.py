#这一部分是使用python写A*算法的，我思考能不能使用A*路径启发式求最短路径
#首先我们要自己写个A*算法是不是，好了，下面就是我打算写的

class SimpleGraph:
	def __init__(self):
		self.edges = {}

	def neighbors(self, id):
		return self.edges[id]

example_graph = SimpleGraph()
example_graph.edges = {
		'A':['B'],
		'B':['A', 'C', 'D'],
		'C':['E', 'A'],
		'E':['B']
		}

import collections

class Queue:
	def __init__(self):
		self.elements = collections.deque()

	def empty(self):
		return len(self.elements) == 0

	def put(self, x):
		self.elements.append(x)

	def get(self):
		return self.elements.popleft()

from implementation import *

def bread_first_search_1(graph, start):
	frontier = Queue()
	frontier.put(start)
	visited = {}
	visited[start] = True;

	while not frontier.empty():
		current = frontier.get()
		print(" Visiting %r" %current)
		for next in graph.neighbors(current):
			if next not in visited:
				frontier.put(next)
				visited[next] = True

breadth_frist_search(example_graph, 'A')
