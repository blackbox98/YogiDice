import csv
import numpy as np

def calccate(game_id): 
    
    gameid_list = open("./yogidice/asset/gameid_list.csv", "r", newline='', encoding='utf-8')
    mecinfo_1001_gameid = open("./yogidice/asset/mecinfo_1001_gameid.csv", "r", newline='', encoding='utf-8')
    mecinfo_0930 = open("./yogidice/asset/mecinfo_0930_noindex.csv", "r", newline='', encoding='utf-8')
    gameid_list = list(csv.reader(gameid_list))
    mecinfo_1001_gameid = list(csv.reader(mecinfo_1001_gameid))
    mecinfo_0930 = list(csv.reader(mecinfo_0930))
    game_id = str(mecinfo_1001_gameid[game_id-1][0])
    
    # game_id = str(input())
    testarr = mecinfo_0930[gameid_list[0].index(game_id)]
    testarr = list(map(int, testarr))

    for i in range(15242):
        mecinfo_0930[i] = list(map(int, mecinfo_0930[i]))

    # 전치행렬으로 만들었음ㄴ
    transpose = np.array(list(np.transpose(mecinfo_0930)))
    testarr = np.array(testarr)

    transpose_result = list(testarr@transpose)

    # print(sorted(testarr@transpose, reverse=True))
    for i in range(15242):
        mecinfo_1001_gameid[i].append(transpose_result[i])
    result = sorted(mecinfo_1001_gameid, key=lambda mecinfo_1001_gameid:mecinfo_1001_gameid[2], reverse=True)[:51]
    #최대 30개 보낼수잇어
    # print(result)

    result_code = []
    result_id = []
    for res in result:
        result_code.append(res[0])
        result_id.append(res[1])


    # 겹치는거 빼기
    real_result = []
    for i in range(51):
        elem = result_code.pop(0)
        if elem in result_code:
            continue
        else:
            real_result.append(result_id[i])
    # print(real_result)


    return (real_result[1:])