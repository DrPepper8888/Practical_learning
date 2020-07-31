import requests,json
from bs4 import BeautifulSoup
import time
import lxml
from selenium import webdriver
#user_Agent
request_headers={
    "User-Agent":'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36'
}

if __name__ == '__main__':
    r = requests.get('https://store.steampowered.com/stats/Steam-Game-and-Player-Statistics?l=schinese',headers=request_headers)
    data=[]
    if r.status_code == 200:
        soup = BeautifulSoup(r.text, 'lxml')
        online_people = soup.find(class_="statsTopHi").string
        print(online_people)
        with open('online_people.txt', 'w', encoding='utf-8') as file:
            file.write(online_people)
    time.sleep(2)