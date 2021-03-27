#!/usr/bin/env python
import re
import sys
import pandas as pd
from numpy import mean
import matplotlib.pyplot as plt

data = []
with open(sys.argv[1]) as file_object:
	line = file_object.readline()
	line = file_object.readline()
	while line:
		sub_data = []
		for i in range(10):
			if(line.startswith('real')):
				real = re.split('\\t|m|s\\n', line)
				real = float(real[1])*60.0 + float(real[2])

				line = file_object.readline()
				line = file_object.readline()
				line = file_object.readline()
				line = file_object.readline()

				sub_data.append(real)
		row = {
			'min': min(sub_data),
			'mean': mean(sub_data),
			'max': max(sub_data),
		}
		data.append(row)

	data = pd.DataFrame(data, columns=['min', 'mean', 'max'])
	data.index = [75, 150, 225, 300, 375, 450, 525, 600, 675, 750]
	data = data.T
	plt.figure(figsize=(18,5))
	plt.plot(data.iloc[0], label='min', color='blue')
	plt.plot(data.iloc[1], label='mean', color='green')
	plt.plot(data.iloc[2], label='max', color='red')
	plt.legend()
	plt.ylabel('time (s)')
	plt.xlabel('number of nodes')
	plt.show()
