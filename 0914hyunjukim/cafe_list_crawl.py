
import time
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver import ActionChains
import csv
from tqdm import tqdm

options = webdriver.ChromeOptions()
options.add_experimental_option("excludeSwitches", ["enable-logging"])
driver = webdriver.Chrome(options=options)
url = 'https://map.kakao.com/'
driver.get(url)

elem = driver.find_element(By.ID, 'search.keyword.query')
elem.send_keys("보드카페")
elem.send_keys("\n")

time.sleep(2)

lists = driver.find_element(By.ID, 'info.search.place.list')
more = driver.find_element(By.ID, 'info.search.place.more')

li = lists.find_elements(By.CSS_SELECTOR, 'li')
divs = li[0].find_elements(By.CSS_SELECTOR, 'div')

ActionChains(driver).move_to_element(divs[6].find_element(By.CSS_SELECTOR, 'span')).click().perform()
ActionChains(driver).move_to_element(divs[6].find_element(By.CSS_SELECTOR, 'span')).click().perform()
time.sleep(2)

# print(more.text)
def crawl():
    time.sleep(1)
    context = driver.find_element(By.ID, 'info.search.place.list')
    for li in context.find_elements(By.CSS_SELECTOR, 'li'):
        div = li.find_elements(By.CSS_SELECTOR, 'div')
        print(len(div))
        if len(div) == 2:
            print('add')
        else:
            print([div[6].text, div[8].text.replace('\n', ''), div[10].text.replace('\n', '')])
            cafe_list.writerow([div[6].text, div[8].text.replace('\n', ''), div[10].text.replace('\n', '')])




cafe_list_file = open("cafe_list.csv", "w", newline='', encoding='utf-8')
cafe_list = csv.writer(cafe_list_file)

crawl()

ActionChains(driver).move_to_element(more).click().perform()

### 여기까지 보드카페 눌렀음

agg = ['no3', 'no4', 'no5', 'next', 'no2']

for i in range(7):
    print('=======', i, '단계야 =======')
    crawl()

    pg3 = driver.find_element(By.ID, 'info.search.page.'+ag)
    print(pg3.text)
    ActionChains(driver).move_to_element(pg3).click().perform()








