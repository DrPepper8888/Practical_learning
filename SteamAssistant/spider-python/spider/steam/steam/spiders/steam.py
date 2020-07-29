# -*- coding: utf-8 -*-
import scrapy
from scrapy import Request
from steam.items import SteamItem
import json
import time
from scrapy.selector import Selector
from selenium import webdriver
from selenium.webdriver.chrome.options import Options  # 使用无头浏览器

# 无头浏览器设置
chorme_options = Options()
# chorme_options.add_argument("--headless")
# chorme_options.add_argument("--disable-gpu")
chorme_options.add_argument(
    "User-Agent=" + "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36")
# 防止被浏览器识别
chorme_options.add_experimental_option('excludeSwitches', ['enable-automation'])


# http://chromedriver.storage.googleapis.com/index.html?path=84.0.4147.30/
class SteamSpider(scrapy.Spider):
    name = 'steam'
    start_url = 'https://store.steampowered.com/search/?filter=globaltopsellers&os=win&page=1'
    # start_url = 'https://steamdb.info/app/962130'
    url = 'https://store.steampowered.com/search/?filter=globaltopsellers&os=win&page='
    totPage = 766
    currPage = 1

    def start_requests(self):
        response = scrapy.Request(self.start_url, callback=self.parse_index, meta={"type": 0})
        yield response

    # 访问主页的url, 拿到对应板块的response
    def parse_index(self, response):
        for item in response.xpath("//*[@id='search_resultsRows']/a"):
            steamItem = SteamItem()
            link = item.xpath("@href").extract_first().strip()
            steamItem['id'] = link.split('/')[4]
            steamItem['name'] = item.xpath(
                "div[@class='responsive_search_name_combined']/div[1]/span/text()").extract_first().strip()
            steamItem['img'] = item.xpath("div[1]/img/@src").extract_first().strip()
            steamItem['price'] = self.format_data_br(
                item.xpath("div[2]/div[contains(@class, 'search_price_discount_combined')]/div[2]/text()").extract())
            # 降价幅度
            steamItem['discount'] = self.format_data_br(item.xpath(
                "div[2]/div[contains(@class, 'search_price_discount_combined')]/div[1]/span/text()").extract_first())
            steamItem['review'] = self.format_data_br(item.xpath(
                "div[2]/div[contains(@class, 'search_reviewscore')]/span/@data-tooltip-html").extract_first())
            yield Request(url=link, callback=self.parse_content, meta={"item": steamItem, "type": 1}, dont_filter=True)
            #         time.sleep(1)

        self.currPage = self.currPage + 1
        url = self.url + str(self.currPage)
        if self.currPage > self.totPage:
            return
        yield Request(url=url, callback=self.parse_index, meta={"type": 0}, dont_filter=True)

    def parse_history(self, response):
        item = response.meta['item']
        str = response.body.decode().strip()
        str = str.split('\">')[1]
        str = str.split('</')[0]
        jsobj = json.loads(str)
        item['history'] = jsobj
        yield item

    def parse_country_price(self, response):
        item = response.meta['item']
        price_list = []
        for box_item in response.xpath('//*[@id="prices"]//table/tbody/tr'):
            item_temp = {}
            item_temp['country'] = self.format_data_br(box_item.xpath("td[1]/text()").extract())
            item_temp['price'] = box_item.xpath("td[4]/text()").extract_first().strip()
            price_list.append(item_temp)
        item['price_list'] = price_list
        url = 'https://steamdb.info/api/GetPriceHistory/?appid=' + item['id'] + '&cc=cn'
        yield Request(url, callback=self.parse_history, meta={"item": item, "type": 2})

    def format_data_br(self, data):
        if data == None:
            return ''
        res = ''
        for item in data:
            item = self.filter_str(item)
            res += item.strip()
        return res

    def filter_str(self, str):
        str.strip("").strip('\r\n').replace(u'\u3000', ' ').replace(u'\xa0', u' ')
        return str

    def parse_content(self, response):
        item = response.meta['item']
        # content = response.meta['con']
        # 开发商
        item['developer'] = self.format_data_br(response.xpath('//*[@id="developers_list"]/a/text()').extract())
        # 发行商
        item['pub'] = self.format_data_br(
            response.xpath("//div[@class='user_reviews']//div[@class='dev_row'][2]/div[2]/a/text()").extract())

        item['tag'] = self.format_data_br(
            response.xpath("//div[@class='glance_tags popular_tags']/a[1]/text()").extract())

        content = ''
        for content_item in response.xpath("//div[contains(@class, 'review_box')]"):
            content = self.format_data_br(content_item.xpath("div/div[2]/div[@class='content']/text()").extract() )+ '-'+content
            print('content======')
            print(content)

        item['content'] =  content
        base_url = 'https://steamdb.info/app/'
        url = base_url + item['id']
        yield Request(url=url, callback=self.parse_country_price, meta={"item": item, "type": 2}, dont_filter=True)
