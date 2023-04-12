import csv
from tqdm import tqdm
import numpy as np

gameid_list = open("gameid_list.csv", "r", newline='', encoding='utf-8')
gameid_list = list(csv.reader(gameid_list))
# print(gameid_list[0])

game_id = str(input())
mecinfo_0930_gameid = open("mecinfo_0930_gameid.csv", "r", newline='', encoding='utf-8')
mecinfo_0930 = open("mecinfo_0930_noindex.csv", "r", newline='', encoding='utf-8')
mecinfo_0930_gameid = list(csv.reader(mecinfo_0930_gameid))
mecinfo_0930 = list(csv.reader(mecinfo_0930))
testarr = mecinfo_0930[gameid_list[0].index(game_id)]
testarr = list(map(int, testarr))

for i in range(15242):
    mecinfo_0930[i] = list(map(int, mecinfo_0930[i]))

# 전치행렬으로 만들었음
transpose = np.array(list(np.transpose(mecinfo_0930)))
testarr = np.array(testarr)

transpose_result = list(testarr@transpose)

# print(sorted(testarr@transpose, reverse=True))
for i in range(15242):
    mecinfo_0930_gameid[i].append(transpose_result[i])
result = sorted(mecinfo_0930_gameid, key=lambda mecinfo_0930_gameid:mecinfo_0930_gameid[1], reverse=True)[:30]
#최대 30개 보낼수잇어

result_id = []
for res in result:
    result_id.append(res[0])


# 겹치는거 빼기
real_result = []
for i in range(30):
    elem = result_id.pop(0)
    if elem in result_id:
        continue
    else:
        real_result.append(elem)
print(real_result)