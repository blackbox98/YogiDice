import csv
from tkinter import E
import pandas as pd
from tqdm import tqdm
import numpy as np
import math
import time
def search(list, score):
  # csv_data = pd.read_csv('../data/newMatrix.csv', delimiter='|', encoding='UTF-8')
  # rating_data = pd.read_csv('../data/newMatrixR.csv', delimiter='|', encoding='UTF-8')
  csv_data = pd.read_csv('./data/newMatrix.csv', delimiter='|', encoding='UTF-8')
  rating_data = pd.read_csv('./data/newMatrixR.csv', delimiter='|', encoding='UTF-8')

  result = []
  resultR = []
  if len(list) == 2:
    list.append(list[1])
    score.append(0)
  elif len(list) == 1:
    list.append(list[0])
    list.append(list[0])
    score.append(0)
    score.append(0)
  
  for j, v in enumerate(list) :
    try :
      data = csv_data['data'][v-1].split(',')
    except :
      list[j] = 1
  
  user = np.zeros(420148, int)
  userR = [ [] for _ in range(420148)]
  i = int(list[0])
  data = csv_data['data'][i-1].split(',')
  dataR = rating_data['data'][i-1].split(',')
  for e, j in enumerate( data ):
    user[int(j)-1] = 1
    userR[int(j)-1].append(dataR[e])
  i = int(list[1])
  user2 = np.zeros(420148, int)
  data = csv_data['data'][i-1].split(',')
  dataR = rating_data['data'][i-1].split(',')
  for e, j in enumerate( data ):
    user2[int(j)-1] = 1
    userR[int(j)-1].append(dataR[e])
  isTrue = False
  for k in range(0, 420148) :
    user2[k] = user[k] * user2[k]
    if user2[k] == 1:
      isTrue = True
  
  if not isTrue:
    for i, v in enumerate(user) :
      if v == 1 :
        result.append(i+1)
        resultR.append(userR[i])
    print("1단계")
    return knn(result, resultR, score)
  i = int(list[2])
  user3 = np.zeros(420148, int)
  data = csv_data['data'][i-1].split(',')
  dataR = rating_data['data'][i-1].split(',')
  for e, j in enumerate( data ):
    user3[int(j)-1] = 1
    userR[int(j)-1].append(dataR[e])
  isTrue = False
  for k in range(0, 420148) :
    user3[k] = user2[k] * user3[k]
    if user3[k] == 1:
      isTrue = True
  if not isTrue:
    for i, v in enumerate(user2) :
      if v == 1 :
        result.append(i+1)
        resultR.append(userR[i])
    print("2단계")
    return knn(result, resultR, score)
  
  for k, v in enumerate(user3) :
    if v == 1 :
      result.append(k+1)
  
  for k in result:
    resultR.append(userR[k -1])
  
  knndata = knn(result, resultR, score)

  final = []
  for e in range(0, 15242):
    try:
      line = csv_data['data'][e].split(',')
    except: continue
    for l, v in enumerate(line) :
      for k , v2 in enumerate(knndata) :
        if int(v) == v2 and not e+1 == list[0] and not e+1 == list[1] and not e+1 == list[2]:
          id = str(e + 1)
          sc = rating_data['data'][e].split(',')[l]
          final.append([id , sc])
  final_temp = []
  final_set = []
  for value in final:
    if value[0] not in final_temp:
        final_temp.append(value[0])
        final_set.append(value)
  final_set = sorted(final_set, key =lambda item: item[1], reverse=True)[0:20]
  final_result = []
  for f in final_set:
    final_result.append(f[0])
  return final_result 


def knn(result, resultR, score) :
  data = []
  for i in resultR:
    data.append(dist(i, score))
  idxes = np.argsort(data)
  K = 3
  answer = []
  for i in range(0,K) :
    answer.append(result[idxes[i]])
  return answer


def dist(score, score2) :
  sum = 0
  for i in score:
    for j in score2:
      sum += (float(i)-float(j))**2
  return math.sqrt(sum)

# start = time.time()
# print(search([1, 2, 3], [5, 8, 3]))
# print(time.time() - start)
