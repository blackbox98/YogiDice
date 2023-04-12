import json
import xmltodict
import requests
import csv
from tqdm import tqdm

url = 'https://api.geekdo.com/xmlapi/boardgame/'

file_m = open("0906_mec.csv", "w", newline='', encoding='utf-8')
bgg = open("bgg.csv", "r", newline='', encoding='utf-8')

wrm = csv.writer(file_m)
bgg_r = csv.reader(bgg)

for line in tqdm(bgg_r):
    num = int(line[0])
    new = [num]
    res = requests.get(url+f'{num}')

    xmltojson = xmltodict.parse(res.content).get('boardgames').get('boardgame')

    if "error" in xmltojson:
        new.append('Item not found')
    else:
        newlist = [num]

        if 'boardgamemechanic' in xmltojson:
            if type(xmltojson.get('boardgamemechanic')) == dict:
                newlist.append(xmltojson.get('boardgamemechanic').get("#text"))
            else:
                cate = xmltojson.get('boardgamemechanic')
                for c in range(len(cate)):
                    newlist.append(cate[c].get("#text"))
        else:
            newlist.append('-')
        
        wrm.writerow(newlist)





    
    
    

    

    

