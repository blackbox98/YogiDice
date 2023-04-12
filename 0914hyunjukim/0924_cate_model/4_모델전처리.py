import json
import pandas as pd
import csv
from tqdm import tqdm

# 1. 대분류 데이터 불러오기
mec_main_dic = json.loads(open("mec_main_dic.text", "r", encoding='utf-8').read())
main_mechanisum_len = {'economic' : 0, 'figure' : 0, 'stratgy' : 0, 'rule' : 0, 'puzzle' : 0, 'party' : 0} # 대분류데이터 갯수

for dic in mec_main_dic:
    main_mechanisum_len[dic] = len(mec_main_dic.get(dic))

# for mm in main_mechanisum:
#     print(mec_main_dic[mm])



# 2. 게임별 매커니즘 세부정보 불러오기
game_mec_info = open("game_mec_info.csv", "r", newline='', encoding='utf-8')
game_mec_info = list(csv.reader(game_mec_info))




# 3. 비율 계산해서 넣기
mec_score_board = pd.DataFrame({'no': [], 'economic' : [], 'figure' : [], 'stratgy' : [], 'rule' : [], 'puzzle' : [], 'party' : []})

# print(game_mec_info)
i = -1
for game in tqdm(game_mec_info):
    i += 1
    arr = [game_mec_info[i][0]]
    count = {'economic' : 0, 'figure' : 0, 'stratgy' : 0, 'rule' : 0, 'puzzle' : 0, 'party' : 0}
    for gm in game[1:]: # 게임에 있는 모든 매커니즘을 돌면
        for mm in main_mechanisum_len: # 매커니즘에 해당하는 요소는 갯수를 세어야됨
            if gm in mec_main_dic[mm]:
                count[mm] += 1
    
    for mec in count:
        # 각 매커니즘 대분류 갯수에 비해 각 게임이 가진 매커니즘이 몇개나되는지 비율으로 계산함
        arr.append((count[mec]/main_mechanisum_len[mec])*10) # 백분율으로 계산
    mec_score_board.loc[i] = arr

mec_score_board.to_csv("mec_score_board.csv", mode='w')

