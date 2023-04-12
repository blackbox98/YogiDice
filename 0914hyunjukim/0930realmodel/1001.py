import csv

mecinfo_0930_gameid = open("mecinfo_0930_gameid.csv", "r", newline='', encoding='utf-8')
mecinfo_1001_gameid = open("mecinfo_1001_gameid.csv", "w", newline='', encoding='utf-8')
mecinfo_0930_gameid = list(csv.reader(mecinfo_0930_gameid))
mecinfo_1001_gameid = csv.writer(mecinfo_1001_gameid)

cnt = 0
for arr in mecinfo_0930_gameid:
    cnt += 1
    arid = [arr[0], cnt]
    print(arr)
    mecinfo_1001_gameid.writerow(arid)

