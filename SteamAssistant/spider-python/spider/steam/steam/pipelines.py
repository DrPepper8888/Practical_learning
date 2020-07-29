# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://doc.scrapy.org/en/latest/topics/item-pipeline.html
import json
class SteamPipeline(object):
    box = []
    def process_item(self, item, spider):
        self.box.append(dict(item))


    def close_spider(self, spider):
        filename = 'result.json'
        with open(filename, 'w') as file_obj:
            json.dump(self.box, file_obj)

        return '保存成功'