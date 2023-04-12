
import time
from selenium import webdriver
from selenium.webdriver.common.by import By
import csv
from tqdm import tqdm


options = webdriver.ChromeOptions()
options.add_experimental_option("excludeSwitches", ["enable-logging"])
driver = webdriver.Chrome(options=options)
# driver = webdriver.Chrome()
cate = 'https://www.divedice.net/board/wiki?ca=%EC%B9%B4%ED%85%8C%EA%B3%A0%EB%A6%AC'
mec = 'https://www.divedice.net/board/wiki?ca=%EB%A9%94%EC%BB%A4%EB%8B%88%EC%A6%98'

cate_urls = []
mec_urls = []

cate_detail_file = open("cate_detail.csv", "w", newline='', encoding='utf-8')
cate_detail = csv.writer(cate_detail_file)
urls = [mec] #[cate, mec]
for url in urls:
    driver.get(url)
    tbody = driver.find_element(By.TAG_NAME, 'tbody')
    rows = tbody.find_elements(By.TAG_NAME, 'tr')


    cate_detail.writerow(['이름', '링크', '상세정보'])
    for tr in tqdm(rows):
        tds = tr.find_elements(By.TAG_NAME, 'td')
        for td in tds:
            cate_elem = []

            name = td.find_element(By.TAG_NAME, 'a').text
            href = td.find_element(By.TAG_NAME, 'a').get_attribute('href')
            # print(name)
            cate_elem.append(name)

            # print(href)
            cate_elem.append(href)

            # options = webdriver.ChromeOptions()
            # options.add_experimental_option("excludeSwitches", ["enable-logging"])
            ndriver = webdriver.Chrome(options=options)
            ndriver.get(str(href))
            add = ndriver.find_element(By.CLASS_NAME, 'memoWrap').text
            cate_elem.append(add)    

            cate_detail.writerow(cate_elem)

# detail_url = 'https://www.divedice.net/kor/board/wiki?viewMode=view&ca=&sel_search=&txt_search=&orderby=&page=1&idx=866'
# driver.get(href)
# add = driver.find_element(By.CLASS_NAME, 'memoWrap').text
# cate_elem.append(add)    