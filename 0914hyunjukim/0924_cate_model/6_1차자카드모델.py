import csv
from tqdm import tqdm

mec_main_all = open('mec_main_all.csv', "r", newline='', encoding='utf-8')
game_mec_info = open('game_mec_info.csv', "r", newline='', encoding='utf-8')
mec_main_all = list(csv.reader(mec_main_all))
game_mec_info = list(csv.reader(game_mec_info))

model_jackard = open('6_model_score.csv', "w", newline='', encoding='utf-8')
model_jackard = csv.writer(model_jackard)

obj_score = []

for info in game_mec_info:
    if info[0] == '167791':
        obj_score = info[1:]
        if '' in obj_score:
            obj_score.remove('')
        obj_score = set(obj_score)
        break
# print(obj_score)
# print(game_mec_info[4])

for info in tqdm(game_mec_info):
    if '' in info:
        info.remove('')
    info = set(info[1:])
    # print((len(obj_score.intersection(info))/len(obj_score.union(info)))*70)
    model_jackard.writerow([(len(obj_score.intersection(info))/len(obj_score.union(info)))*70])


# for info in game_mec_info:
#     print(info[1:])