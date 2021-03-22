#!/usr/bin/env python
import re
import pandas as pd
import matplotlib.pyplot as plt

data = []
with open('benchtime.txt') as file_object:
	line = file_object.readline()
	while line:
		if(line.startswith('real')):
			real = re.split('\\t|m|s\\n', line)
			real = float(real[1])*60.0 + float(real[2])
			
			line = file_object.readline()
			user = re.split('\\t|m|s\\n', line)
			user = float(user[1])*60.0 + float(user[2])
			
			line = file_object.readline()
			sys = re.split('\\t|m|s\\n', line)
			sys = float(sys[1])*60.0 + float(sys[2])
			
			row = {
				'real': real,
				'user': user,
				'sys': sys,
			}
			data.append(row)

		else:
			line = file_object.readline()

	data = pd.DataFrame(data, columns=['real', 'user', 'sys'])
	data.index = [75, 150, 225, 300, 375, 450, 525, 600, 675, 750]
	data = data.T
	print(data.T)
	plt.figure(figsize=(18,5))
	plt.plot(data.iloc[0], label='real', color='blue')
	plt.plot(data.iloc[1], label='user', color='green')
	plt.plot(data.iloc[2], label='sys', color='red')
	plt.legend()
	plt.ylabel('time (s)')
	plt.xlabel('number of nodes')
	plt.show()
