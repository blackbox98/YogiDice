import csv
import pandas as pd
from tqdm import tqdm


mec_score_board = open("mec_score_board.csv", "r", newline='', encoding='utf-8')
mec_score_board = list(csv.reader(mec_score_board))

print(mec_score_board[4])

obj_score = []

for i in mec_score_board:
    if i[1] == '167791':
        obj_score = i[2:]
        break
# print(obj_score)

calc_score_abs = pd.DataFrame({'no': [], 'economic' : [], 'figure' : [], 'stratgy' : [], 'rule' : [], 'puzzle' : [], 'party' : [], 'sum_score' : []})


for score in tqdm(range(1, len(mec_score_board))):
    # print(mec_score_board[score])
    arr = [mec_score_board[score][1]]
    scr = mec_score_board[score][2:]
    sum_score = 0
    for i in range(6):
        column_sc = 5-(abs(float(scr[i])-float(obj_score[i]))*0.1*5)
        arr.append(column_sc)
        sum_score += column_sc
    arr.append(sum_score)
    # print(arr)
    calc_score_abs.loc[score] = arr

print(calc_score_abs)
