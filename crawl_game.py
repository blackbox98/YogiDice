from msilib.schema import Error
from threading import currentThread
from time import sleep
import pandas as pd
from selenium import webdriver as wb
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.keys import Keys
from bs4 import BeautifulSoup as bs
import lxml
import re
import requests
import time
import csv
import os
from tqdm import tqdm

def main(no):
    no = int(no)
    page = int(no / 100)
    if no % 100 == 0:
        page = page -1
    page = page + 1
    cnt = no - 1

    options = wb.ChromeOptions()
    options.add_experimental_option("excludeSwitches", ["enable-logging"])
    driver = wb.Chrome("./chromedriver", options=options)
    driver.get('https://boardlife.co.kr/rank.php?pg=' + str(page) + '&type=&number=')

    title_kor = ''
    title_eng = ''
    year = ''
    score_bl = ''
    score_user =''
    gametype = ''
    gametheme = ''
    mechanisms = ''
    image = ''
    bggcode = ''
    minplayers = ''
    maxplayers = ''
    playingtime = ''
    minplaytime = ''
    maxplaytime = ''
    age = ''
    averageweight = ''
    index = True
    isre = False
    
    while index:
        soup = bs(driver.page_source, 'html.parser')
        page_bar = soup.find('div', class_="paging-btn")
        pages = page_bar.find_all('a')
        page_now = soup.find('a', class_="now").text
        page = []
        for i in pages:
            page.append(i.text)
        index_now = page.index(page_now)
        if '다음' in page:
            index_next = page.index('다음')
        else:
            index_next = len(page)
            if index_now == len(page) -1 :
                index = False

        table_title = soup.find_all('div', class_="storage-title")
        table_year = soup.find_all('div', class_="storage-year")
        table_score_bl = soup.find_all('div', class_="storage-avg")
        table_score_user = soup.find_all('div', class_="storage-point")
        table_image = soup.select(".storage-thumb > img")

        if not isre:
            startpoint = (no-1) % 100
        else : startpoint = 0
        
        for i in range(startpoint, len(table_title)):
            image = (table_image[i]['src'])
            current_time = time.time()
            cnt = cnt + 1
            print('no : '+ str(cnt))
            title_kor = (table_title[i].text)
            # print('game : ' + table_title[i].text + ' | ' + re.sub(r'[^0-9]', '',table_year[i].text))
            year = (re.sub(r'[^0-9]', '',table_year[i].text))
            score_bl = (table_score_bl[i].text)
            score_user = (table_score_user[i].text)
            driver.find_element("xpath", '/html/body/div[5]/div[1]/div[2]/div[2]/a[' + str(i+1) + ']').click()
            soup = bs(driver.page_source, 'html.parser')
            code = ''
            isfind = False
            try:
                code = soup.select_one('div.game-intro-div-title > a')
                code = code['href']
                if 'boardgamegeek' in code:
                    if not '/boardgame/' in code:
                       raise Exception
                    code = str(code).split('/')[4]
                    bggcode = (code)
                    # print('code : ' + code)
                    isfind = True
                else : raise Exception
                # driver.find_element("xpath", '//*[@id="game-overview-box"]/div[2]/div[1]/div[6]/a').click()
                tempurl = 'https://boardgamegeek.com/boardgame/' + code
                script = f"window.open('{tempurl}');"
                driver.execute_script(script)
            except Exception as e:
                findtitle = soup.select_one('h2').text
                findyear = re.sub(r'[^0-9]', '',table_year[i].text)
                # print(findtitle + ' ' + findyear)
            
                response = requests.get('https://boardgamegeek.com/xmlapi/search?search=' + findtitle)
                content = response.text
                xml_obj = bs(content,'lxml-xml')
                rows = xml_obj.select('boardgame')
                code = ''
                
                if len(rows) == 0:
                    findtitle = findtitle.split()[0]  
                    response = requests.get('https://boardgamegeek.com/xmlapi/search?search=' + findtitle)
                    content = response.text
                    xml_obj = bs(content,'lxml-xml')
                    rows = xml_obj.select('boardgame')
                for i in range(0,len(rows)):
                    if len(rows) == 1:
                        code = rows[i]['objectid']
                        code = str(code)
                        # print(code)
                        isfind = True
                        break
                    elif (rows[i].find('name').text).lower() == findtitle.lower() :
                        code = rows[i]['objectid']
                        code = str(code)
                        # print(code)
                        isfind = True
                        break
                if not isfind:
                    try :
                        code = rows[0]['objectid']
                        code = str(code)
                        # print(code)
                        isfind = True
                    except Exception as e:
                        isfind = False
                        driver.back()
                        continue
                bggcode = code
                tempurl = 'https://boardgamegeek.com/boardgame/' + code
                script = f"window.open('{tempurl}');"
                driver.execute_script(script)
            if not isfind:
                driver.close()
                driver.switch_to.window(driver.window_handles[0])
                driver.back()
                continue   
            driver.switch_to.window(driver.window_handles[-1])
            urlcode = driver.current_url.split('/')[4]
            if not urlcode == code:
                bggcode = urlcode
                code = urlcode
            response = requests.get('https://boardgamegeek.com/xmlapi/game/' + code + '?stats=1')
            content = response.text
            sleep(1)
            xml_obj = bs(content, 'lxml-xml')
            try : minplayers = (xml_obj.select_one('minplayers').text)
            except Exception as e:
                response = requests.get('https://boardgamegeek.com/xmlapi/game/' + code + '?stats=1')
                content = response.text
                minplayers = (xml_obj.select_one('minplayers').text)
            maxplayers = (xml_obj.select_one('maxplayers').text)
            playingtime = (xml_obj.select_one('playingtime').text)
            minplaytime = (xml_obj.select_one('minplaytime').text)
            maxplaytime = (xml_obj.select_one('maxplaytime').text)
            age = (xml_obj.select_one('age').text)
            averageweight = (xml_obj.select_one('averageweight').text)
            
            # print('minplayers : ' + xml_obj.select_one('minplayers').text + ' | maxplayers : ' +xml_obj.select_one('maxplayers').text)
            # print('playingtime : ' +xml_obj.select_one('playingtime').text + ' | minplaytime : ' +xml_obj.select_one('minplaytime').text + ' | maxplaytime : ' +xml_obj.select_one('maxplaytime').text)
            # print('age : ' +xml_obj.select_one('age').text)
            # print('averageweight : ' +xml_obj.select_one('averageweight').text)

            soup = bs(driver.page_source, 'html.parser')
            try : 
                eng_title = soup.select_one('h1 > a').text
            except Exception as e:
                driver.refresh()
                soup = bs(driver.page_source, 'html.parser')
                eng_title = soup.select_one('h1 > a').text
            eng_title = eng_title.strip()
            title_eng = (eng_title)
            # print('title : '+eng_title)
            driver.find_element("xpath", '//*[@id="mainbody"]/div[2]/div/div[1]/div[2]/ng-include/div/div/ui-view/ui-view/div/overview-module/description-module/div/div[2]/div/div[1]/classifications-module/div/div[2]/ul/li[1]/div[2]/button').click()
            sleep(1)
            soup = bs(driver.page_source, 'html.parser')
            typetable = soup.select('tbody > tr')
            temptype = ''
            for l in range(0,8):
                typeitem = str(typetable[l])
                soup = bs(typeitem, 'lxml-xml')
                per = soup.select_one('td > span')
                if 'ng-binding is-winner' in str(per):
                    temptype = soup.select_one('th > span > a').text
                    gametype = (temptype)
            # print('type : ' + temptype)
            driver.find_element("xpath", '/html/body/div[1]/div/div/div[2]/button[2]').click()
            sleep(1)
            driver.find_element("xpath", '//*[@id="primary_tabs"]/ul/li[12]/button').click()
            sleep(1)
            driver.find_element("xpath", '/html/body/div[4]/div/div[1]/ul/li[9]/a').click()
            sleep(1)
            soup = bs(driver.page_source, 'html.parser')
            tempcate = soup.find_all('li', class_='outline-item ng-scope')
            tempstr = ''
            try : 
                strcate = str(tempcate[13])
                soup = bs(strcate, 'lxml-xml')
            except Exception as e:
                driver.refresh()
                soup = bs(driver.page_source, 'html.parser')
                tempcate = soup.find_all('li', class_='outline-item ng-scope')
                tempstr = ''
                strcate = str(tempcate[13])
                soup = bs(strcate, 'lxml-xml')
            temp = soup.select('a')
            for l in temp:
                tempstr = tempstr + l.text + ','
            tempstr = tempstr[1:]
            tempstr = tempstr[:-1]
            gametheme = (tempstr)
            # print('theme : '+ tempstr)
            strmecha = str(tempcate[14])
            tempstr = ''
            soup = bs(strmecha, 'lxml')
            temp = soup.select('a')
            for l in temp:
                tempstr = tempstr + l.text + ','
            tempstr = tempstr[1:]
            tempstr = tempstr[:-1]
            mechanisms = (tempstr)
            # print('mechanisms : ' + tempstr)
            ctime = int(time.time() - current_time)
            # print('실행시간 : ' + str(ctime) + '초')
            # print('================================================================================')
           
            driver.close()
            driver.switch_to.window(driver.window_handles[0])
            driver.back()
        
        # '''csv 저장'''
            info = pd.DataFrame(
            {"이름": [title_kor], "이름(eng)": [title_eng], "년도": [year], "img": [image], "점수(bl)": [score_bl], 
            "점수(user)": [score_user], "분류" : [gametype], "테마" : [gametheme], "진행방식" : [mechanisms],
            "bggcode" : [bggcode], "최소인원" : [minplayers], "최대인원" : [maxplayers], "시간" : [playingtime], "최소시간" : [minplaytime], "최대시간" : [maxplaytime], "연령" : [age], "난이도" : [averageweight]})
            if not os.path.exists('보드게임목록.csv'):
                info.to_csv('보드게임목록.csv',  mode='w', header=True, index=False, encoding='utf-8-sig', sep='|')  
            else:
                info.to_csv('보드게임목록.csv',  mode='a', header=False, index=False, encoding='utf-8-sig', sep='|')
            print(' success')
        isre = True
        # 페이지넘기기 / 종료
        if index :
            if int(index_now) + 1 < int(index_next):
                next_index = int(index_now) + 2
                driver.find_element("xpath", '/html/body/div[5]/div[1]/div[3]/div/a[' + str(next_index) + ']').click()
            elif int(index_now) + 1 == int(index_next):
                next_next = int(index_next) + 1
                driver.find_element("xpath", '/html/body/div[5]/div[1]/div[3]/div/a[' + str(next_next) + ']').click()
          
 
no = input()
main(no)