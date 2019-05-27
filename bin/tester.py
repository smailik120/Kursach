import numpy as np
import pandas as pd
from scipy.stats import bernoulli
import re
p = []
game = []
f = open('src//java.txt', 'r');
currentRow = 0
columns = 0
for line in f:
    game.append(re.sub("[^\w]", " ", line).split())
    currentRow += 1
f.close()
columns = len(game[0])
win = []
loose = []
for i in range(0, columns):
    win.append(1)
    loose.append(1)
for i in range(0, columns):
    p.append(np.random.beta(1, 1))
maximum = -100
indexTheta = 0
indexBestStrategy = 0
minimum = 10000000
for j in range(1, 1000):
    rand = np.random.randint(0, currentRow)#номер атаки
    for i in range(0, columns):#выбор в тета массиве
        if p[i] > maximum:
            maximum = p[i]
            indexTheta = i
    for i in range(0, columns):# проверка совпадения выйгрыш ли
        if int(game[rand][i]) < minimum:
            minimum = int(game[rand][i])
            indexBestStrategy = i
    if indexBestStrategy == indexTheta:
        p[indexTheta] = np.random.beta(win[indexTheta] + 1, loose[indexTheta])
        win[indexTheta] += 1
    else:
        p[indexTheta] = np.random.beta(win[indexTheta], loose[indexTheta] + 1)
        loose[indexTheta] += 1
    maximum = -100
    indexTheta = 0
    indexBestStrategy = 0
    minimum = 10000000
for i in range(0, columns):  # выбор в тета массиве
    if p[i] > maximum:
        maximum = p[i]
        indexTheta = i
f = open('src//python.txt', 'w');
f.write(str(indexTheta + 1))
f.close()

