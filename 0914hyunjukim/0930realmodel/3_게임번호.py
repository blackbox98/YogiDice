import csv

mecinfo_0930_gameid = open("mecinfo_0930_gameid.csv", "r", newline='', encoding='utf-8')
mecinfo_0930_gameid = list(csv.reader(mecinfo_0930_gameid))

arsr = []
for arr in mecinfo_0930_gameid:
    arsr.append(arr[0])

gameid_list = open("gameid_list.csv", "w", newline='', encoding='utf-8')
gameid_list = csv.writer(gameid_list)
gameid_list.writerow(arsr)