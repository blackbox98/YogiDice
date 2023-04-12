import csv
file_m = open("메커니즘.csv", "r", newline='', encoding='utf-8')
file_d = open("mec_main.csv", "w", newline='', encoding='utf-8')
mec = csv.reader(file_m)
mecdic = csv.writer(file_d)

cnt = 0
meca = [[], [], [], [], [], [], []]
# print(meca[3])
for line in mec:
    print(type(int(line[3])))
    meca[int(line[3])].append(line[1])
print(meca)
for i in meca:
    mecdic.writerow(i)