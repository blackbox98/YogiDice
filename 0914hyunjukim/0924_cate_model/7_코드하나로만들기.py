import csv
import pandas as pd
from tqdm import tqdm


mec_score_board = open("mec_score_board.csv", "r", newline='', encoding='utf-8')
mec_score_board = list(csv.reader(mec_score_board))

mec_main_all = open('mec_main_all.csv', "r", newline='', encoding='utf-8')
game_mec_info = open('game_mec_info.csv', "r", newline='', encoding='utf-8')
mec_main_all = list(csv.reader(mec_main_all))
game_mec_info = list(csv.reader(game_mec_info))

game_id = str(input())

obj_score = []
obj_score_2 = []

for i in mec_score_board:
    if i[1] == game_id:
        obj_score = i[2:]
        break
# print(obj_score)

for info in game_mec_info:
    if info[0] == game_id:
        obj_score_2 = info[1:]
        if '' in obj_score_2:
            obj_score_2.remove('')
        obj_score_2 = set(obj_score_2)
        break

# calc_score_abs = pd.DataFrame({'no': [],'sum_score' : [], 'jk_score' : [], 'result' : []})
calc_score_abs = pd.DataFrame({'no': [], 'result' : []})


for score in tqdm(range(len(mec_score_board))):
    # print(mec_score_board[score])
    arr = [mec_score_board[score][1]]
    scr = mec_score_board[score][2:]
    sum_score = 0
    if '' in game_mec_info[score]:
        game_mec_info[score].remove('')
    info = set(game_mec_info[score][1:])
    # print((len(obj_score.intersection(info))/len(obj_score.union(info)))*70)
    jk = (len(obj_score_2.intersection(info))/len(obj_score_2.union(info)))*70
    # arr.append(jk)
    if jk != 0:
        for i in range(6):
            column_sc = 5-(abs(float(scr[i])-float(obj_score[i]))*0.1*5)
            sum_score += column_sc
        # arr.append(sum_score)
        # print(arr)
        arr.append(sum_score+jk)
    else:
        arr.append(0)

    calc_score_abs.loc[score] = arr

calc_score_abs = calc_score_abs.sort_values(by=["result"], ascending=[False]) 
print(calc_score_abs)
calc_score_abs.to_csv("calc_score_abs.csv", mode='w')

result = []
calc_score_abs.sort_values(by=["result"], ascending=False, inplace=True) 
# for i in range(11):
#     result.append(calc_score_abs.loc[i]['no'])

result = []

cnt = 0
for score in calc_score_abs.iterrows():
    cnt += 1
    if cnt == 11:
        break
    result.append(calc_score_abs.loc[score[0]]['no'])

# print(result)