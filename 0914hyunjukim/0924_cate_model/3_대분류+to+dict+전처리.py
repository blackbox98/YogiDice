import csv
import json

mec_main = open("mec_main.csv", "r", newline='', encoding='utf-8')              # 매커니즘 대분류
mec_main_dic = open("mec_main_dic.text", "w", encoding='utf-8')                 # 대분류 딕셔너리 형태로 저장

mec_main = list(csv.reader(mec_main))             # 매커니즘 대분류

main_dic = {}
main_dic["economic"] = mec_main[0]
main_dic["figure"] = mec_main[1]
main_dic["stratgy"] = mec_main[2]
main_dic["rule"] = mec_main[3]
main_dic["puzzle"] = mec_main[4]
main_dic["party"] = mec_main[5]

result = json.dumps(main_dic, indent=4)
print(result)
mec_main_dic.write(result)
