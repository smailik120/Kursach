import numpy as np
import pandas as pd
import re
f = open('C:\\test\\java.txt','r');
rows = 0;
columns = 0;
matrix = [];
for line in f:
    matrix.append(re.sub("[^\w]", " ", line).split())
    rows+=1;
f.close();
variants = []
for t in range(1, rows - 1):
    variants.append(t)
a = np.ones(len(matrix[0]))
b = np.ones(len(matrix[0]))
theta = np.zeros(len(matrix[0]))
temp = 0
ind = 0
maxim = 0
attack = 0
reward = 0
min = 100000000
average = np.ones(len(matrix[0]));
b1 = 1000;
step = np.ones(len(matrix[0]))
mean = np.zeros(len(matrix[0]))
for i in range(1,50000):
    for j in range(0, len(matrix[0])):
        theta[j] = np.random.beta(a[j], b[j])
        if(theta[j] >= maxim) :
            maxim = theta[j]
            ind = j;
    min = 100000000
    maxim = 0;
    attack = np.random.randint(0, rows);
    for k in range(0, len(matrix[0])):
        average[k] = 1/step[k] * (mean[k] + int(matrix[attack][k]))
        if average[k] < min:
            min = average[k]
    mean[ind] = mean[ind] + int(matrix[attack][ind])
    if (average[ind] == min):
        a[ind] = a[ind] + 0.01
        step[ind] = step[ind] + 1;
    else:
        b[ind] = b[ind] + 0.01
min = 10000000;
print(average)
print(theta)
print(ind + 1)
f = open('C:\\test\\python.txt','w');
f.write(str(ind + 1))
f.close()