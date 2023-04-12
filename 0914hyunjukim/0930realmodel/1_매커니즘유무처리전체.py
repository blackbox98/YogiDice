import csv
from tqdm import tqdm
import numpy as np

mec_main_all = open("mec_main_all.csv", "r", newline='', encoding='utf-8')
game_mec_info = open("game_mec_info.csv", "r", newline='', encoding='utf-8')
mec_main_all = list(csv.reader(mec_main_all))
game_mec_info = list(csv.reader(game_mec_info))

# mecinfo_0930 = open("mecinfo_0930.csv", "w", newline='', encoding='utf-8')
# mecinfo_0930 = open("mecinfo_0930_noindex.csv", "w", newline='', encoding='utf-8')
mecinfo_0930 = open("mecinfo_0930_gameid.csv", "w", newline='', encoding='utf-8')
mecinfo_0930 = csv.writer(mecinfo_0930)


# 매커니즘이 있으면 1처리, 없으면 0처리하는 작업

for i in range(15242):
    # mec_info = [0 for _ in range(193)] ## 게임번호 있는버전
    # mec_info = [0 for _ in range(192)] ## 게임번호 없는버전
    mec_info = [0] 
    # print(game_mec_info[i])
    mec_info[0] = int(game_mec_info[i][0])
    # for info in game_mec_info[i][1:]:
    #     if info == '-' or info == '':
    #         continue
    #     mec_info[mec_main_all[0].index(info)] = 1
    print(mec_info)
    mecinfo_0930.writerow(mec_info)