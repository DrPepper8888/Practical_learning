# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# https://doc.scrapy.org/en/latest/topics/items.html

import scrapy


class SteamItem(scrapy.Item):
    # define the fields for your item here like:
    name = scrapy.Field()  #
    review = scrapy.Field()  # 整体评价
    img = scrapy.Field()  # 封面
    price = scrapy.Field()  # 价格
    discount = scrapy.Field()  # 降价
    id = scrapy.Field()  # 游戏id
    developer = scrapy.Field()  # 开发商
    pub = scrapy.Field()  # 发行商
    tag = scrapy.Field()  # 标签
    content = scrapy.Field()  # 评价
    price_list = scrapy.Field()  # list
    history = scrapy.Field()  # list

    # print(name)
    # print(review)
    # print(img)
    # print(self.format_data_br(price))
    # print(discount)
    # print(id)
    # yield response
