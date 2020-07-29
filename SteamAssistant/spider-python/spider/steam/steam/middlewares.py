# -*- coding: utf-8 -*-

# Define here the models for your spider middleware
#
# See documentation in:
# https://doc.scrapy.org/en/latest/topics/spider-middleware.html

from scrapy import signals
from selenium.webdriver import Chrome, ChromeOptions
from scrapy.http.response.html import HtmlResponse
import time


class SteamSpiderMiddleware(object):
    # Not all methods need to be defined. If a method is not defined,
    # scrapy acts as if the spider middleware does not modify the
    # passed objects.

    @classmethod
    def from_crawler(cls, crawler):
        # This method is used by Scrapy to create your spiders.
        s = cls()
        crawler.signals.connect(s.spider_opened, signal=signals.spider_opened)
        return s

    def process_spider_input(self, response, spider):
        # Called for each response that goes through the spider
        # middleware and into the spider.

        # Should return None or raise an exception.
        return None

    def process_spider_output(self, response, result, spider):
        # Called with the results returned from the Spider, after
        # it has processed the response.

        # Must return an iterable of Request, dict or Item objects.
        for i in result:
            yield i

    def process_spider_exception(self, response, exception, spider):
        # Called when a spider or process_spider_input() method
        # (from other spider middleware) raises an exception.

        # Should return either None or an iterable of Response, dict
        # or Item objects.
        pass

    def process_start_requests(self, start_requests, spider):
        # Called with the start requests of the spider, and works
        # similarly to the process_spider_output() method, except
        # that it doesn’t have a response associated.

        # Must return only requests (not items).
        for r in start_requests:
            yield r

    def spider_opened(self, spider):
        spider.logger.info('Spider opened: %s' % spider.name)


class SteamDownloaderMiddleware(object):
    # Not all methods need to be defined. If a method is not defined,
    # scrapy acts as if the downloader middleware does not modify the
    # passed objects.

    @classmethod
    def from_crawler(cls, crawler):
        # This method is used by Scrapy to create your spiders.
        s = cls()
        crawler.signals.connect(s.spider_opened, signal=signals.spider_opened)
        return s

    def process_request(self, request, spider):
        # Called for each request that goes through the downloader
        # middleware.

        # Must either:
        # - return None: continue processing this request
        # - or return a Response object
        # - or return a Request object
        # - or raise IgnoreRequest: process_exception() methods of
        #   installed downloader middleware will be called
        return None

    def process_response(self, request, response, spider):
        # Called with the response returned from the downloader.

        # Must either;
        # - return a Response object
        # - return a Request object
        # - or raise IgnoreRequest
        return response

    def process_exception(self, request, exception, spider):
        # Called when a download handler or a process_request()
        # (from other downloader middleware) raises an exception.

        # Must either:
        # - return None: continue processing this exception
        # - return a Response object: stops process_exception() chain
        # - return a Request object: stops process_exception() chain
        pass

    def spider_opened(self, spider):
        spider.logger.info('Spider opened: %s' % spider.name)


class SeleniumDownloadMiddleware(object):
    def __init__(self):
        # 防止爬虫检测
        option = ChromeOptions()
        option.add_argument(
            "User-Agent=" + "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36")
        # 防止被浏览器识别
        option.add_argument('--ignore-certificate-errors')
        option.add_experimental_option('excludeSwitches', ['enable-automation'])
        self.driver = Chrome(options=option)
    # 整个爬虫结束后关闭浏览器
    def close(self,spider):
        self.driver.quit()
    def process_request(self, request, spider):
        if 'type' in request.meta.keys():
            type = request.meta['type']
        else:
            type = 0
        print(request.url)
        if type==1:
            self.driver.set_page_load_timeout(4)
            self.driver.get(request.url)
            self.driver.execute_script("window.scrollTo(100, document.body.scrollHeight);")
            time.sleep(2)
            # try:

            #     # while True:
            # content =''
            # for content_item in self.driver.find_element_by_xpath("//div[contains(@class, 'review_box')]"):
            #         content = content_item.find_element_by_xpath("div/div[2]/div[@class='content']")+content
            #         print('content======')
            #         print(content)
            # if content:
            #     request.meta['con'] = content.text
            # else:
            #     request.meta['con'] = ''
                #
                # # 展开文章
                # showMore = self.driver.find_elements_by_xpath(
                #     "//div[@style='text-align:center; margin-top:-5px;']//a[@style='text-decoration:none;']")
                # for item in showMore:
                #     self.driver.execute_script('arguments[0].click()', item)
                #     time.sleep(1)
            # except:
            #     request.meta['con'] = ''
            source = self.driver.page_source
            response = HtmlResponse(url=self.driver.current_url, body=source, request=request, encoding="utf-8")
            return response
        if type == 2:
            self.driver.set_page_load_timeout(10)
            self.driver.get(request.url)
            time.sleep(6)

            source = self.driver.page_source
            response = HtmlResponse(url=self.driver.current_url, body=source, request=request, encoding="utf-8")
            return response
        if type ==0 :
            return
        source = self.driver.page_source
        response = HtmlResponse(url=self.driver.current_url, body=source, request=request, encoding="utf-8")
        return response
